import Deliveries.BusinessLayer.DeliveryFormsController;
import Deliveries.BusinessLayer.DeliveryManagerImpl;
import Deliveries.PresentationLayer.CLI.CLI;
import Deliveries.PresentationLayer.GUI.View.MainMenuFrame;
import HR.PresentationLayer.GUI.View.LoginFrame;
import HR.PresentationLayer.Menu;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class Deliveries_HR_Main {

    public static void main(String[] args) throws SQLException {
        loadData();
        if (Objects.equals(args[0], "GUI") && Objects.equals(args[1], "Deliveries")) {
            SwingUtilities.invokeLater(MainMenuFrame::new);
        } else if (Objects.equals(args[0], "GUI") && Objects.equals(args[1], "HR")) {
            SwingUtilities.invokeLater(LoginFrame::new);
        } else if (Objects.equals(args[0], "CLI") && Objects.equals(args[1], "Deliveries")) {
            CLI.main(args);
        } else if (Objects.equals(args[0], "CLI") && Objects.equals(args[1], "HR")) {
            Menu.main(args);
        } else {
            System.out.println("Welcome! Please choose which system you'd like to enter:");
            System.out.println("1. HR System");
            System.out.println("2. Deliveries System");
            System.out.println("To run the GUI, please run the program with the argument 'GUI' ");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.nextLine();
            switch (option) {
                case "1" -> Menu.main(args);
                case "2" -> CLI.main(args);
            }
        }
    }

    private static void loadData() {
        DeliveryManagerImpl deliveryManager = DeliveryManagerImpl.getInstance();
        DeliveryFormsController deliveryFormsController = DeliveryFormsController.getInstance();
        deliveryFormsController.loadFormsData();
        // TODO: Add HR data loading?
    }


}
