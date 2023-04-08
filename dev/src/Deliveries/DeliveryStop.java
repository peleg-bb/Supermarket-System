package Deliveries;

import java.util.Map;

public class DeliveryStop {
    private int deliveryId;
    private Map<String, Integer> items;
    private Site origin;
    private Site destination;
    private TruckType truckTypeRequired;

    @Override
    public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("DeliveryStop{")
                    .append("deliveryId=")
                    .append(deliveryId)
                    .append(", deliveryItems={");
            for (Map.Entry<String, Integer> entry : items.entrySet()) {
                sb.append(entry.getKey())
                        .append(": ")
                        .append(entry.getValue())
                        .append(", ");
            }
            sb.delete(sb.length() - 2, sb.length());
            sb.append("}, from origin- ")
                    .append(origin)
                    .append(", to destination- ")
                    .append(destination)
                    .append(", truckTypeRequired- ")
                    .append(truckTypeRequired)
                    .append('}');
            return sb.toString();
    }

    public DeliveryStop(int deliveryId, Map<String, Integer> items, Site origin, Site destination, TruckType truckTypeRequired) {
        this.deliveryId = deliveryId;
        this.items = items;
        this.destination = destination;
        this.truckTypeRequired = truckTypeRequired;
        this.origin=origin;
    }

    // Getters

    public TruckType getTruckTypeRequired() {
        return truckTypeRequired;
    }

    public Map<String, Integer> getItems(){
        return items;
    }

    public Site getOrigin(){
        return origin;
    }

    public Site getDestination(){
        return destination;
    }


    public int getDeliveryId() {
        return deliveryId;
    }
}
