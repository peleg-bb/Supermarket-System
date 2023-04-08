package Deliveries;

import java.util.HashMap;

public class TruckController {
    private HashMap<String,Truck> trucks;

    private static TruckController instance = null;
    // Singleton Constructor
    private TruckController() {
        trucks = new HashMap<>();
        generateTruckFleet(20);

    }

    public static TruckController getInstance() {
        if (instance == null) {
            instance = new TruckController();
        }
        return instance;
    }


    public Truck pickTruck(TruckType requiredType) throws DeliveryException{
        for (Truck curr : trucks.values()) {
            if (curr.getType().equals(requiredType)) {
                if (curr.getAvailability().equals(Availability.Available)) {
                    return curr;
                }
            }
        }
        // No available trucks of the required type
        throw new DeliveryException("No available trucks of type " + requiredType);
    }



    public Truck pickTruck(TruckType requiredType, int requiredWeight) throws DeliveryException{
        for (Truck curr : trucks.values()) {
            if (curr.getType().equals(requiredType) && curr.getMaxWeightTons() >= requiredWeight) {
                if (curr.getAvailability().equals(Availability.Available)) {
                    return curr;
                }
            }
        }
        // No available trucks of the required type and weight
        throw new DeliveryException("No available trucks of type " + requiredType + " and weight " + requiredWeight);
    }

    public void freeTruck(String truckID){
        trucks.get(truckID).setAvailability(Availability.Available);
    }

    private void generateTruckFleet(int numTrucks) {
        String[] truckModels = {"Volvo", "Mercedes-Benz", "Scania", "MAN", "Daf", "Iveco"};
        String[] licensePlates = {"ABC123", "DEF456", "GHI789", "JKL012", "MNO345", "PQR678"};
        TruckType[] truckTypes = TruckType.values();
        int[] maxWeights = {10, 20, 30, 40, 50};
        int[] netWeights = {3, 5, 7, 9, 11};

        for (int i = 0; i < numTrucks; i++) {
            String model = truckModels[(int)(Math.random() * truckModels.length)];
            String licensePlate = licensePlates[(int)(Math.random() * licensePlates.length)];
            TruckType type = truckTypes[(int)(Math.random() * truckTypes.length)];
            int maxWeight = maxWeights[(int)(Math.random() * maxWeights.length)];
            int netWeight = netWeights[(int)(Math.random() * netWeights.length)];

            Truck truck = new Truck(model, licensePlate, type, maxWeight, netWeight);
            truck.setAvailability(Availability.Available);
            addTruck(truck);
        }
    }

    private void addTruck(Truck truck) {
        trucks.put(truck.getLicensePlate(), truck);
    }

}
