package Deliveries;

import java.util.ArrayList;
import java.util.List;

public class DriverController {
    private List<Driver> drivers;

    private static DriverController instance = null;
    // Singleton Constructor
    private DriverController() {
        drivers = new ArrayList<Driver>();

    }

    public static DriverController getInstance() {
        if (instance == null) {
            instance = new DriverController();
        }
        return instance;
    }


    public Driver pickDriver(TruckType truckType, int weight) throws DeliveryException{
        for(int i=0; i<drivers.size(); i++){
            Driver curr = drivers.get(i);
            if(curr.getAvailability().equals(Availability.Available)){
                if(curr.getLicense().getTruckTypesAllowed().equals(truckType)&&curr.getLicense().getWeightAllowedTons()==weight){
                    return curr;
                }
            }
        }
        throw new DeliveryException("No available drivers with license for truck type "
                + truckType + " and weight " + weight);
    }
}
