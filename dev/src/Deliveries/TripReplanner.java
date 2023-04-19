package Deliveries;

import java.util.List;

public interface TripReplanner {
    public DeliveryStop removeStop(List<DeliveryStop> stops);
    public DeliveryStop removeItems(List<DeliveryStop> stops);
    public int chooseAction(List<DeliveryStop> stops);
}
