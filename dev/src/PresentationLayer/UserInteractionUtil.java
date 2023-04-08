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
        System.out.println("The truck is overloaded, and we couldn't arrange a larger truck." +
                " You have to remove some stops.");
        System.out.println("These are the stops you have to visit: ");
        int i = 0;
        for (DeliveryStop stop : stops) {
            i++;
            System.out.println("Stop number " + i + ": " + stop);
        }
        System.out.println("Which stops would you like to remove?");
        // ask for an int. if not int, ask again
        while (!scanner.hasNextInt()) {
            System.out.println("Enter truck weight: ");
            scanner.next();
        }
        int quantity = scanner.nextInt();
        stops.remove(quantity-1);
        return stops;
    }
}
