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
    private Site originSite;
    private DeliveryManagerImpl deliveryManager;
    private int dispatchWeightTons; // Weight of the truck when it leaves the origin site

    public DeliveryForm(int formId, List<DeliveryStop> stops, Site originSite, int maxWeightAllowed){
        this.formId = formId;
        this.destinationSitesToVisit = stops;
        //date = Date.
        //time
        this.originSite = originSite;
        this.maxWeightAllowed = maxWeightAllowed;
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

}
