import Deliveries.PresentationLayer.CLI.CLI;
import HR.PresentationLayer.Menu;

import java.sql.SQLException;
import java.util.Scanner;

public class Deliveries_HR_Main {

    public static void main(String[] args) throws SQLException {
        System.out.println("Welcome! Please choose which system you'd like to enter:");
        System.out.println("1. HR System");
        System.out.println("2. Deliveries System");
        System.out.println("3. Deliveries GUI");
        Scanner scanner = new Scanner(System.in);
        String option = scanner.nextLine();
        switch (option) {
            case "1" -> Menu.main(args);
            case "2" -> CLI.main(args);
            case "3" -> new Deliveries.PresentationLayer.GUI.MainFrame();
        }
    }


}
