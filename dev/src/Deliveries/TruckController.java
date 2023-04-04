package Deliveries;

import java.util.ArrayList;
import java.util.List;

public class TruckController {
    private List<Truck> trucks;

    private static TruckController instance = null;
    // Singleton Constructor
    private TruckController() {
        trucks = new ArrayList<Truck>();

    }

    public static TruckController getInstance() {
        if (instance == null) {
            instance = new TruckController();
        }
        return instance;
    }


    public Truck pickTruck(TruckType requiredType) throws DeliveryException{
        for(int i=0; i<trucks.size(); i++){
            Truck curr = trucks.get(i);
            if(curr.getType().equals(requiredType)){
                if(curr.getAvailability().equals(Availability.Available)){
                    return curr;
                }
            }
        }
        throw new DeliveryException("No available trucks of type " + requiredType);
    }
}
