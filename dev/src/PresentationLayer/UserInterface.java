package PresentationLayer;

import Deliveries.DeliveryManagerImpl;
import Deliveries.Site;

import java.util.HashMap;
import java.util.Scanner;

public class UserInterface {
    // main method
    public static void main(String[] args) {
        // create a new DeliveryFormsController
        DeliveryManagerService deliveryManagerService = new DeliveryManagerService();
        DeliveryManagerImpl deliveryManager = deliveryManagerService.deliveryManager;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the delivery manager!");
        System.out.println("Please enter details about the origin of the delivery: ");
        Site originBranch = getSite(scanner);
        while (true) {
            System.out.println("Would you like to add a delivery stop? (Y/N)");
            String answer = scanner.nextLine();
            if (answer.equals("Y")) {
                Site destinationBranch = getSite(scanner);
                while (true) {
                    HashMap<String, Integer> deliveryItems = new HashMap<>();
                    System.out.println("Would you like to add an item? (Y/N)");
                    String answer2 = scanner.nextLine();
                    if (answer2.equals("Y")) {
                        String item = askForItem(scanner);
                        int quantity = askForQuantity(scanner);
                        deliveryItems.put(item, quantity);
                    } else if (answer2.equals("N")) {
                        deliveryManager.addDeliveryStop(deliveryItems, originBranch, destinationBranch);
                        break;
                    }
                }
                break;
            }
        }
    }

    private static Site getSite(Scanner scanner) {
        System.out.println("Please enter the branch name: ");
        String branchName = scanner.nextLine();
        // ask for an address
        System.out.println("Please enter the address of the branch: ");
        String branchAddress = scanner.nextLine();
        // ask for a contact name
        System.out.println("Please enter the contact name of the branch: ");
        String branchContactName = scanner.nextLine();
        // ask for a contact phone
        System.out.println("Please enter the contact phone of the branch: ");
        String branchContactPhone = scanner.nextLine();
        // ask for a delivery zone
        System.out.println("Please enter the delivery zone of the branch: ");
        String branchDeliveryZone = scanner.nextLine();
        // create a new branch
        Site branch = new Site(branchName, branchAddress, branchContactName, branchContactPhone, branchDeliveryZone);
        return branch;
    }

    public static String askForItem(Scanner scanner){
        System.out.println("Please enter the item: ");
        String item = scanner.nextLine();
        return item;
    }

    public static int askForQuantity(Scanner scanner){
        System.out.println("Please enter the quantity: ");
        int quantity = scanner.nextInt();
        return quantity;
    }

    public static void printMenu() {
        System.out.println("Please enter the number of the option you would like to choose: ");
        System.out.println("1. Add a delivery stop");
        System.out.println("2. Remove a delivery stop");
        System.out.println("3. Exit");
    }




}
