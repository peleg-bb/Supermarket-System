package Deliveries;

import java.util.ArrayList;
import java.util.List;

public class DeliveryManagerImpl implements DeliveryManager{
    private TruckController truckController;
    private DriverController driverController;
    private List<DeliveryStop> deliveryStops;
    private int deliveryCount;
    private  int deliveryFormCount;
    private static DeliveryManagerImpl instance = null;
    // Singleton Constructor
   private DeliveryManagerImpl() {
            deliveryCount = 0;
            deliveryFormCount = 0;
            deliveryStops = new ArrayList<DeliveryStop>();
            truckController = TruckController.getInstance();
            driverController = DriverController.getInstance();
    }

    public static DeliveryManagerImpl getInstance() {
        if (instance == null) {
            instance = new DeliveryManagerImpl();
        }
        return instance;
    }

    @Override
    public void addDeliveryStop(List<String> items, Site origin, Site destination) {
        DeliveryStop deliveryStop = new DeliveryStop(++deliveryCount, items, destination);
        // decide how to manage the origin.
    }

    @Override
    public void removeDeliveryStop(int deliveryId) {


    }

    public void createForm(List<DeliveryStop> stops, TruckType truckType, Site origin){
        Truck t = truckController.pickTruck(truckType);
        Driver d = null;
        if(t!=null){
            d = driverController.pickDriver(t.getType(), 100);//TODO: fix the weight
        }
        else {
            //if t is null?
        }

        if(d!=null){
            //what if it's null?
        }
        else{
            DeliveryForm form = new DeliveryForm(deliveryFormCount++, stops, origin,d,t,t.getType(),100 );//TODO: fix weight
        }

    }


    }


