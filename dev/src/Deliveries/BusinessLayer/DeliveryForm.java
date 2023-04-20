package Deliveries.BusinessLayer;

import Deliveries.BusinessLayer.Enums_and_Interfaces.DeliveryStatus;
import Deliveries.BusinessLayer.Enums_and_Interfaces.TruckType;
import Deliveries.BusinessLayer.Enums_and_Interfaces.WeightMeasurer;
import Deliveries.PresentationLayer.UserInteractionUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

public class DeliveryForm {
    private final int formId;
    private Date date;
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

    public DeliveryForm(int formId, List<DeliveryStop> stops, Site originSite, int maxWeightAllowed,
                        Driver driver, Truck truck){
        this.formId = formId;
        this.destinationSitesToVisit = stops;
        this.destinationSitesVisited = new ArrayList<>();
        dispatchTime = new Timestamp(System.currentTimeMillis());
        // date = today?
        date = new Date();
        this.originSite = originSite;
        this.maxWeightAllowed = maxWeightAllowed;
        this.driver = driver;
        this.truck = truck;
        this.weightMeasurer = new UserInteractionUtil();
        deliveryManager = DeliveryManagerImpl.getInstance();
        deliveryFormsController = DeliveryFormsController.getInstance();
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

        destinationSitesVisited.add(deliveryStop);
        deliveryStop.setStatus(DeliveryStatus.DELIVERED);
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
        TruckType truckType = TruckType.Regular;
        // for each delivery stop, check if the truck type is the same
        for (DeliveryStop stop : destinationSitesToVisit) {
            if (stop.getTruckTypeRequired() == TruckType.Refrigerated) {
                truckType = TruckType.Refrigerated;
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
        truck.freeTruck();
        truck = newTruck;
        setMaxWeightAllowed(truck.getMaxWeightTons());
    }
}