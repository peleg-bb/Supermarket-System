package PresentationLayer;

import Deliveries.DeliveryManagerImpl;
import Deliveries.Site;

import java.util.HashMap;
import java.util.Scanner;

public class UserInterface {
    // main method
    public static void main(String[] args) {
        // create a new DeliveryFormsController
        //DeliveryManagerService deliveryManagerService = new DeliveryManagerService();
        Scanner scanner = new Scanner(System.in);
        printMenu();
        int ans = scanner.nextInt();
        if(ans==1) {
            DeliveryManagerImpl deliveryManager = DeliveryManagerImpl.getInstance(); //removed the use of service class
            System.out.println("Welcome to the delivery manager!");
            while (true) {
                System.out.println("Would you like to add a delivery stop? (Y/N)");
                String answer = scanner.nextLine();
                if (answer.equals("Y") || answer.equals("y")) {
                    System.out.println("please enter the origin details");
                    Site originBranch = getSite(scanner);
                    System.out.println("please enter the destination details");
                    Site destinationBranch = getSite(scanner);
                    while (true) {
                        HashMap<String, Integer> deliveryItems = new HashMap<>();
                        System.out.println("Would you like to add an item? (Y/N)");
                        String answer2 = scanner.nextLine();
                        if (answer2.equals("Y") || answer.equals("y")) {
                            String item = askForItem(scanner);
                            int quantity = askForQuantity(scanner);
                            deliveryItems.put(item, quantity);
                        } else if (answer2.equals("N") || answer.equals("n")) {
                            deliveryManager.addDeliveryStop(deliveryItems, originBranch, destinationBranch);
                            //System.out.println("delivery ID is");

                            break;
                        }
                    }
                } else if (answer.equals("N") || answer.equals("n")) {
                    break;
                }
            }
        } else if (ans==2) {
            System.out.println("enter the stop you want to remove:");
            //we dont have deliveryID for removing
        } else if (ans==3) {
            //exit
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
