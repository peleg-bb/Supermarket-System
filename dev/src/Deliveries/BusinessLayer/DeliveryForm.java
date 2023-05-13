package Deliveries.BusinessLayer;

import Deliveries.BusinessLayer.Enums_and_Interfaces.DeliveryException;
import Deliveries.BusinessLayer.Enums_and_Interfaces.DeliveryStatus;
import Deliveries.BusinessLayer.Enums_and_Interfaces.TruckType;
import Deliveries.BusinessLayer.Enums_and_Interfaces.WeightMeasurer;
import Deliveries.PresentationLayer.UserInteractionUtil;
import HR.BusinessLayer.ShiftController;
import HR_Deliveries_Interface.HRIntegrator;

import java.sql.Timestamp;
import java.util.*;

public class DeliveryForm {
    private final int formId;
    private Timestamp dispatchTime;
    private WeightMeasurer weightMeasurer;
    private List<DeliveryStop> destinationSitesToVisit;
    private List<DeliveryStop> destinationSitesVisited;
    private int maxWeightAllowed;
    private Driver driver;
    private Truck truck;
    private Site originSite;
    private DeliveryManagerImpl deliveryManager;
    private DeliveryFormsController deliveryFormsController;
    private DeliveryStop stopToCancel;
    private int dispatchWeightTons; // Weight of the truck when it leaves the origin site
    private HRIntegrator hrManager;

    private DeliveryStatus status;

    public DeliveryForm(int formId, List<DeliveryStop> stops, Site originSite, Timestamp dispatchTime) throws
            DeliveryException {
        this.formId = formId;
        this.destinationSitesToVisit = stops;
        this.destinationSitesVisited = new ArrayList<>();
        this.status = DeliveryStatus.NOT_STARTED;
        this.dispatchTime = dispatchTime;
        this.originSite = originSite;
        this.weightMeasurer = new UserInteractionUtil();
        deliveryManager = DeliveryManagerImpl.getInstance();
        deliveryFormsController = DeliveryFormsController.getInstance();
        hrManager = ShiftController.getInstance();
        updateArrivalTimes();
        updateFormIDinStops();
    }

    /*
    * This constructor is used when a delivery form is loaded from the database
    */
    public DeliveryForm(int formId, List<DeliveryStop> stopsToVisit,List<DeliveryStop> stopsVisited,
                        Site originSite, Timestamp dispatchTime, DeliveryStatus status) throws DeliveryException {
        this.formId = formId;
        this.destinationSitesToVisit = stopsToVisit;
        this.destinationSitesVisited = stopsVisited;
        this.dispatchTime = dispatchTime;
        this.originSite = originSite;
        this.weightMeasurer = new UserInteractionUtil();
        deliveryManager = DeliveryManagerImpl.getInstance();
        deliveryFormsController = DeliveryFormsController.getInstance();
        hrManager = ShiftController.getInstance();
        updateArrivalTimes();
        setVisitedStopsArrivalTimes();
    }

    /*
    To be used for testing purposes only
     */
    public DeliveryForm(int formId, List<DeliveryStop> stops, Site originSite, Timestamp dispatchTime, HRIntegrator hr)
            throws DeliveryException {
        this.formId = formId;
        this.destinationSitesToVisit = stops;
        this.destinationSitesVisited = new ArrayList<>();
        this.dispatchTime = dispatchTime;
        this.originSite = originSite;
        this.weightMeasurer = new UserInteractionUtil();
        deliveryManager = DeliveryManagerImpl.getInstance();
        deliveryFormsController = DeliveryFormsController.getInstance();
        hrManager = hr;
        updateArrivalTimes();
        //updateFormIDinStops();  // do we actually want to persist if it's a test?
    }

    private void updateFormIDinStops() {
        for(DeliveryStop stop : destinationSitesToVisit){
            stop.setFormID(formId);
        }
    }


    public void addDeliveryStop(DeliveryStop deliveryStop) {
        destinationSitesToVisit.add(deliveryStop);
    }

    public void visitDeliveryStop(DeliveryStop deliveryStop) {
        if (destinationSitesVisited.contains(deliveryStop) ||
                deliveryStop.getStatus() == DeliveryStatus.DELIVERED ||
                deliveryStop.equals(stopToCancel) ||
                deliveryStop.getStatus() == DeliveryStatus.CANCELLED)
        {
            return; // Already visited or cancelled
        }

        deliveryStop.setStatus(DeliveryStatus.DELIVERED); // update status (also in DB)
        destinationSitesVisited.add(deliveryStop);
        performWeightCheck();
    }

    public void performWeightCheck() {
        int currentWeight = measureWeight();
        setDispatchWeightTons(currentWeight);
        if (currentWeight > maxWeightAllowed) {
            deliveryManager.replanDelivery(this);
        }
    }

    private int measureWeight() {
        return weightMeasurer.measureWeight(this);
    }

    @Override
    public String toString() {
        return "DeliveryForm{" +
                "formId=" + formId +
                ", dispatchTime=" + dispatchTime +
                ", originSite=" + originSite +
                ", destinationSitesToVisit=" + destinationSitesToVisit +
                ", dispatchWeightTons=" + dispatchWeightTons +
                '}';
    }

    /* setDispatchWeightTons() is called when the truck leaves the origin site */
    public void setDispatchWeightTons(int dispatchWeightTons) {
        this.dispatchWeightTons = dispatchWeightTons;
    }

    // setMaxWeightAllowed() is called when the truck is loaded with items
    private void setMaxWeightAllowed(int maxWeightAllowed) {
        this.maxWeightAllowed = maxWeightAllowed;
        if (dispatchWeightTons > maxWeightAllowed) {
            deliveryManager.replanDelivery(this);
        }
    }

    public void setDestinationSitesToVisit(List<DeliveryStop> destinationSitesToVisit) {
        this.destinationSitesToVisit = destinationSitesToVisit;
    }

    public int getFormId() {
        return formId;
    }

    public List<DeliveryStop> getDestinationSitesToVisit() {
        return destinationSitesToVisit;
    }

    public List<DeliveryStop> getDestinationSitesVisited() {
        return destinationSitesVisited;
    }

    public TruckType getTruckType() {
        TruckType truckType = TruckType.REGULAR;
        // for each delivery stop, check if the truck type is the same
        for (DeliveryStop stop : destinationSitesToVisit) {
            if (stop.getTruckTypeRequired() == TruckType.REFRIGERATED) {
                truckType = TruckType.REFRIGERATED;
                break;
            }
        }
        return truckType;
    }

    public int getDispatchWeightTons() {
        return dispatchWeightTons;
    }

    public void startJourney(){
        // visit the stops in the order they were added
        performWeightCheck();
        ListIterator<DeliveryStop> iterator = destinationSitesToVisit.listIterator();
        while(iterator.hasNext()){
            DeliveryStop currentStop = iterator.next();
            visitDeliveryStop(currentStop);
            if (currentStop.getStatus() == DeliveryStatus.DELIVERED ||
                    currentStop.equals(stopToCancel) ||
                    currentStop.getStatus() == DeliveryStatus.CANCELLED) {
                iterator.remove();
                // Needs testing
            }
        }
        completeJourney();

    }

    private void completeJourney() {
        driver.freeDriver();
        truck.freeTruck();
        deliveryFormsController.terminateDeliveryForm(this);
    }

    public void cancelForm() {
        driver.freeDriver();
        truck.freeTruck();
        // for delivery stops that were not visited, set their status to cancelled
        for (DeliveryStop stop : destinationSitesToVisit) {
            if (stop.getStatus() != DeliveryStatus.DELIVERED) { // Not sure if this is needed, but just in case
                stop.setStatus(DeliveryStatus.CANCELLED);
                deliveryManager.addDeliveryStop(stop);
            }
        }
    }

    public void cancelStop(DeliveryStop stop) {
        stopToCancel = stop;
        stop.setStatus(DeliveryStatus.CANCELLED);
    }

    public void setWeightMeasurer(WeightMeasurer weightMeasurer) {
        // This is used for testing
        this.weightMeasurer = weightMeasurer;
    }


    public int getMaxWeightAllowed() {
        return maxWeightAllowed;
    }

    public void setTruck(Truck newTruck) {
        if (truck != null){
            truck.freeTruck();
        }
        truck = newTruck;
        setMaxWeightAllowed(truck.getMaxWeightTons());
    }

    public void setDriver(Driver newDriver) {
        if (driver != null){
            driver.freeDriver();
        }
        this.driver = newDriver;
    }

    private void updateArrivalTimes() throws DeliveryException {
        for (DeliveryStop stop : destinationSitesToVisit) {
            stop.updateArrivalTime(dispatchTime);
            if (!hrManager.checkStoreAvailability(stop.getDestination().getName(), stop.getEstimatedArrivalTime())) {
                // TODO: decide what to do if the site is not available
                throw new DeliveryException("destination site is not available at the estimated arrival time");
            }
        }
    }

    private void setVisitedStopsArrivalTimes() throws DeliveryException {
        for (DeliveryStop stop : destinationSitesVisited) {
            stop.updateArrivalTime(dispatchTime);
        }
    }

    public List<DeliveryStop> getStopsByTime(Timestamp startTime, Timestamp finishTime, String siteName){
        List<DeliveryStop> stops = new ArrayList<>();
        for (DeliveryStop stop : destinationSitesToVisit) {
            if (stop != null && stop.getEstimatedArrivalTime().after(startTime) &&
                    stop.getEstimatedArrivalTime().before(finishTime) &&
                    stop.getDestination().getName().equals(siteName)) {
                stops.add(stop);
            }
        }
        return stops;
    }

    public Timestamp getDispatchTime() {
        return dispatchTime;
    }

    public Driver getDriver() {
        return driver;
    }

    public Truck getTruck() {
        return truck;
    }

    public Site getOriginSite() {
        return originSite;
    }

    public Timestamp getEstimatedTerminationTime() {
        // Returns the estimated arrival time of the last stop
        Timestamp lastStop = destinationSitesToVisit.get(0).getEstimatedArrivalTime();
        for (DeliveryStop stop : destinationSitesToVisit) {
            if (stop.getEstimatedArrivalTime().after(lastStop)) {
                lastStop = stop.getEstimatedArrivalTime();
            }
        }
        return lastStop;
    }

    private void setStatus(DeliveryStatus status) {
        this.status = status;
        // TODO: notify DB
    }

    public DeliveryStatus getStatus() {
        return status;
    }

}