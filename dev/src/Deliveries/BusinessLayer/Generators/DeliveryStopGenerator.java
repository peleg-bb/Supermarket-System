package Deliveries.BusinessLayer.Generators;

import Deliveries.BusinessLayer.DeliveryStop;
import Deliveries.BusinessLayer.Enums_and_Interfaces.TruckType;
import Deliveries.BusinessLayer.Site;
import Deliveries.DataAccessLayer.DeliveryStopDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DeliveryStopGenerator {
    private static final int MAX_ITEMS = 20; // Maximum number of items per delivery
    private static final int MAX_QUANTITY = 500; // Maximum quantity per item
    private static final int MAX_TRUCK_TYPE_ORDINAL = TruckType.values().length; // Maximum ordinal value of a truck type

    private final List<Site> sites;
    private final Random random;

    public DeliveryStopGenerator() {
        SiteGenerator siteGenerator = new SiteGenerator();
        sites = siteGenerator.getSitesList();
        this.random = new Random();
    }

    /**
     * Generates a list of delivery stops
     * @param startingIndex The index of the first delivery stop to be generated
     * @param size The number of delivery stops to be generated
     * @return A list of delivery stops
     */
    public List<DeliveryStop> getDeliveryStops(int startingIndex, int size) {
        DeliveryStopDAO deliveryStopDAO = new DeliveryStopDAO();
        List<DeliveryStop> deliveryStops = new java.util.ArrayList<>(deliveryStopDAO.loadData().stream().toList());
        if (deliveryStops.size() < size) {
            for (int i = startingIndex; i < startingIndex + size; i++) {
                DeliveryStop deliveryStop = generateDeliveryStop(i);
                deliveryStopDAO.addStop(deliveryStop);
                deliveryStops.add(deliveryStop);
            }
        }
        return deliveryStops;
    }

    private DeliveryStop generateDeliveryStop(int shipmentInstanceID) {
        Site origin = sites.get(random.nextInt(sites.size()));
        // Generate a site that is different from the origin
        Site destination;
        do {
            destination = sites.get(random.nextInt(sites.size()));
        } while (destination.equals(origin));

        Map<String, Integer> items = generateItems();

        TruckType truckTypeRequired = TruckType.values()[random.nextInt(MAX_TRUCK_TYPE_ORDINAL)];

        return new DeliveryStop(shipmentInstanceID, items, origin, destination, truckTypeRequired);
    }


    private Map<String, Integer> generateItems() {
        Map<String, Integer> items = new HashMap<>();
        int numItems = random.nextInt(MAX_ITEMS) + 1; // Generate a random number of items between 1 and MAX_ITEMS

        String[] groceryNames = { "Apple", "Banana", "Bread", "Butter", "Cheese",
                "Eggs", "Milk", "Orange", "Potato", "Tomato", "Watermelon", "Yogurt", "Zucchini", "Pasta",
                "Rice", "Chicken", "Beef", "Pork", "Fish", "Shrimp", "Salmon", "Tuna", "Crab", "Lobster"};

        for (int i = 0; i < numItems; i++) {
            String itemName = groceryNames[random.nextInt(groceryNames.length)];
            int quantity = random.nextInt(MAX_QUANTITY) + 1; // Generate a random quantity between 1 and MAX_QUANTITY
            items.put(itemName, quantity);
        }

        return items;
    }


}