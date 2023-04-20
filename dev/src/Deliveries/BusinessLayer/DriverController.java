package Deliveries.BusinessLayer;

import Deliveries.BusinessLayer.Enums_and_Interfaces.Availability;
import Deliveries.BusinessLayer.Enums_and_Interfaces.DeliveryException;
import Deliveries.BusinessLayer.Enums_and_Interfaces.TruckType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class DriverController {
    private final HashSet<Driver> drivers;

    private static DriverController instance = null;

    // Singleton Constructor
    private DriverController() {
        drivers = new HashSet<>();
        generateFleet(20);

    }

    public void generateFleet(int numberOfDrivers) {
        Set<String> usedIds = new HashSet<>();
        Random random = new Random();

        for (int i = 0; i < numberOfDrivers; i++) {
            // Generate random driver data
            String name = "Driver " + i;
            String phone = "555-555-" + String.format("%04d", i);

            // Generate unique driver ID
            String id;
            do {
                id = String.format("%09d", random.nextInt(1000000000));
            } while (usedIds.contains(id));
            usedIds.add(id);

            // Create a new driver object and set availability
            Driver driver = new Driver(name, id, phone);
            driver.setAvailability(Availability.Available);

            // Add the driver to the DriverController instance
            addDriver(driver);
        }
    }

    private void addDriver(Driver driver) {
        drivers.add(driver);
    }

    public static DriverController getInstance() {
        if (instance == null) {
            instance = new DriverController();
        }
        return instance;
    }


    public Driver pickDriver(TruckType truckType, int weight) throws DeliveryException {
        for (Driver curr : drivers) {
            if (curr.getAvailability().equals(Availability.Available)) {
                License license = curr.getLicense();
                if (curr.getLicense().getTruckTypesAllowed().contains(truckType)
                        && curr.getLicense().getWeightAllowedTons() >= weight) {
                    curr.setAvailability(Availability.Busy);
                    return curr;
                }
            }
        }
        throw new DeliveryException("No available drivers with license for truck type "
                + truckType + " and weight " + weight);
    }

}

