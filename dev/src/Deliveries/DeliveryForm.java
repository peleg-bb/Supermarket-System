package Deliveries;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class DeliveryForm {
    private String formId;
    private Date date;
    private Timestamp dispatchTime;
    private List<DeliveryStop> destinationSites;
    private Site originSite;
    private Driver driver;
    private Truck truck;
    private TruckType truckTypeRequired; // Might not be needed, depends on future requirements
    private int dispatchWeightTons; // Weight of the truck when it leaves the origin site

    public void addDeliveryStop(DeliveryStop deliveryStop) {
        destinationSites.add(deliveryStop);
    }

}
