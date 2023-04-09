package Deliveries;

import java.util.List;

public interface TripReplanner {
    public List<DeliveryStop> removeStops(List<DeliveryStop> stops);
    public List<DeliveryStop> removeItems(List<DeliveryStop> stops);
    public int chooseAction(List<DeliveryStop> stops);
}
