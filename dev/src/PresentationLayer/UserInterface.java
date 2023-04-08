package PresentationLayer;

import Deliveries.*;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class UserInterface {
    // main method
    public static void main(String[] args) {
        // create a new DeliveryFormsController
        // DeliveryManagerService deliveryManagerService = new DeliveryManagerService();
        Scanner scanner = new Scanner(System.in);
        SiteGenerator siteGenerator = new SiteGenerator();
        List<Site> sitesList = siteGenerator.getSitesList();
        System.out.println("Welcome to the delivery manager!");
        System.out.println("The following sites were auto generated and can be used for this demo: ");
        for (Site site : sitesList) {
            // print the site and its index in the list
            System.out.println(sitesList.indexOf(site) + "- " + site);
        }
        System.out.println();
        System.out.println();
        System.out.println("In order to launch a delivery, you should first provide details.");
        System.out.println("A delivery consists of a list of delivery stops");
        System.out.println("Each delivery stop contains an origin, a destination and a list of items to deliver");
        printMenu();
        int ans = scanner.nextInt();
        DeliveryManagerImpl deliveryManager = DeliveryManagerImpl.getInstance(); //removed the use of service class
        DeliveryFormsController deliveryFormsController = deliveryManager.getDeliveryFormsController();
        while (ans != 4) {
            if (ans == 1) {
                addDeliveryStop(scanner, deliveryManager, sitesList);
            } else if (ans == 2) {
                System.out.println("Enter the stop you want to remove:");
                int id = scanner.nextInt();
                deliveryManager.removeDeliveryStop(id);
            } else if (ans == 3) {
                deliveryManager.createDeliveryGroup();
                System.out.println("The following delivery forms were created:");
                deliveryFormsController.printDeliveryForms();
                System.out.println("Execute deliveries? (Y/N)");
                String answer = scanner.next();
                if (answer.equals("Y") || answer.equals("y")) {
                    for (DeliveryForm deliveryForm : deliveryFormsController.getDeliveryForms()) {
                        deliveryFormsController.startDeliveryForm(deliveryForm);
                    }
                    System.out.println("Delivery executed successfully!");
                } else if (answer.equals("N") || answer.equals("n")) {
                    System.out.println("Deliveries were not executed");
                }
            }
            printMenu();
            ans = scanner.nextInt();
        }
    }

    private static void addDeliveryStop(Scanner scanner, DeliveryManagerImpl deliveryManager, List<Site> sitesList) {
        System.out.println("Here you can add a delivery stop to the pool of visits to be made");
        while (true) {
            System.out.println("Would you like to add a delivery stop? (Y/N)");
            String answer = scanner.next();
            if (answer.equals("Y") || answer.equals("y")) {
                System.out.println("Please enter the origin details");
                Site originBranch = getSite(scanner, sitesList);
                System.out.println();
                System.out.println("Origin is set! Please enter the details about the destinations");
                System.out.println("Please enter the destination details");
                Site destinationBranch = getSite(scanner, sitesList);
                HashMap<String, Integer> deliveryItems = new HashMap<>();
                while (true) {
                    System.out.println("Would you like to add an item to deliver to "
                            + destinationBranch.getName() + "? (Y/N)");
                    String answer2 = scanner.next();
                    if (answer2.equals("Y") || answer2.equals("y")) {
                        String item = askForItem(scanner);
                        int quantity = askForQuantity(scanner);
                        deliveryItems.put(item, quantity);
                    } else if (answer2.equals("N") || answer2.equals("n")) {
                        int id = deliveryManager.addDeliveryStop(deliveryItems, originBranch, destinationBranch);
                        System.out.println("Delivery added successfully. The delivery ID is " + id);

                        break;
                    }
                }
            } else if (answer.equals("N") || answer.equals("n")) {
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

    private static Site getSite(Scanner scanner, List<Site> sitesList) {
        System.out.println("Please enter the index (shown on top) of the site you would like to choose: ");
        // ask for an int. if not int, ask again
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid quantity: ");
            scanner.next();
        }
        int SiteIndex = scanner.nextInt();
        return sitesList.get(SiteIndex);
    }

    public static String askForItem(Scanner scanner){
        System.out.println("Please enter the item name: ");
        String item = scanner.next();
        return item;
    }

    public static int askForQuantity(Scanner scanner){
        System.out.println("Please enter the quantity: ");
        // ask for an int. if not int, ask again
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid quantity. Please enter a valid quantity: ");
            scanner.next();
        }
        int quantity = scanner.nextInt();

        return quantity;
    }

    public static void printMenu() {
        System.out.println();
        System.out.println();
        System.out.println("Please enter the number of the option you would like to choose: ");
        System.out.println("1. Add a delivery stop");
        System.out.println("2. Remove a delivery stop");
        System.out.println("3. Execute a delivery");
        System.out.println("4. Exit");
        System.out.print("Your choice: ");
    }


}
