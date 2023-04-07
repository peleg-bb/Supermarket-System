package Deliveries;

import java.util.HashMap;

public class TruckController {
    private HashMap<String,Truck> trucks;

    private static TruckController instance = null;
    // Singleton Constructor
    private TruckController() {
        trucks = new HashMap<>();

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

    public Truck pickTruck(TruckType requiredType, int requiredWeight) throws DeliveryException{
        for(int i=0; i<trucks.size(); i++){
            Truck curr = trucks.get(i);
            if(curr.getType().equals(requiredType) && curr.getMaxWeightTons() >= requiredWeight){
                if(curr.getAvailability().equals(Availability.Available)){
                    return curr;
                }
            }
        }
        throw new DeliveryException("No available trucks of type " + requiredType + " and weight " + requiredWeight);
    }

    public void freeTruck(String truckID){
        trucks.get(truckID).setAvailability(Availability.Available);
    }
}
