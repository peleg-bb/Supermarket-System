package Deliveries;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class DeliveryForm {
    private int formId ;
    private Date date;
    private Timestamp dispatchTime;
    private List<DeliveryStop> destinationSitesToVisit;
    private List<DeliveryStop> destinationSitesVisited;
    private int maxWeightAllowed;
    private String driverID;
    private String truckID;
    private Site originSite;
    private DeliveryManagerImpl deliveryManager;
    private int dispatchWeightTons; // Weight of the truck when it leaves the origin site

    public DeliveryForm(int formId, List<DeliveryStop> stops, Site originSite, int maxWeightAllowed, String driverID, String truckID){
        this.formId = formId;
        this.destinationSitesToVisit = stops;
        //date = Date.
        //time
        this.originSite = originSite;
        this.maxWeightAllowed = maxWeightAllowed;
        this.driverID = driverID;
        this.truckID = truckID;
    }
    public void addDeliveryStop(DeliveryStop deliveryStop) {
        destinationSitesToVisit.add(deliveryStop);
    }

    public void visitDeliveryStop(DeliveryStop deliveryStop) {
        destinationSitesToVisit.remove(deliveryStop);
        destinationSitesVisited.add(deliveryStop);
    }

    /* setDispatchWeightTons() is called when the truck leaves the origin site */
    public void setDispatchWeightTons(int dispatchWeightTons) {
        this.dispatchWeightTons = dispatchWeightTons;
        if (dispatchWeightTons > maxWeightAllowed) {
            deliveryManager.replanDelivery(this);
            // Notify UI
        }
    }

    // setMaxWeightAllowed() is called when the truck is loaded with items
        public void setMaxWeightAllowed(int maxWeightAllowed) {
        this.maxWeightAllowed = maxWeightAllowed;
        if (dispatchWeightTons > maxWeightAllowed) {
            deliveryManager.replanDelivery(this);
        }
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


    
}