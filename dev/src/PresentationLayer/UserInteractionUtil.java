package PresentationLayer;

import Deliveries.*;

import java.util.List;
import java.util.Scanner;

public class UserInteractionUtil implements WeightMeasurer, TripReplanner {
    Scanner scanner;
    public UserInteractionUtil() {
        this.scanner = new Scanner(System.in);
    }
    @Override
    public int measureWeight(DeliveryForm form) {
        System.out.println("What's the current weight of the truck?");
        // ask for an int. if not int, ask again
        while (!scanner.hasNextInt()) {
            System.out.println("Enter truck weight: ");
            scanner.next();
        }
        int quantity = scanner.nextInt();

        return quantity;
    }

    @Override
    public List<DeliveryStop> removeStops(List<DeliveryStop> stops) {
        System.out.println("These are the stops you have to visit: " + stops);
        System.out.println("Which stops would you like to remove?");

        return stops;
    }
}
