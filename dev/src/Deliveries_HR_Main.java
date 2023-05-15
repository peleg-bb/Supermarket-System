import Deliveries.PresentationLayer.UserInterface;
import HR.BusinessLayer.JobType;
import HR.BusinessLayer.ShiftType;
import HR.PresentationLayer.HRCommandParser;
import HR.PresentationLayer.HRMenu;
import HR.PresentationLayer.Menu;
import HR.ServiceLayer.Response;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class Deliveries_HR_Main {

    public static void main(String[] args) throws SQLException {
        System.out.println("Welcome! Please choose which system you'd like to enter:");
        System.out.println("1. HR System");
        System.out.println("2. Deliveries System");
        Scanner scanner = new Scanner(System.in);
        String option = scanner.nextLine();
        switch (option) {
            case "1" -> {
                Menu.main(args);
            }
            case "2" -> {
                UserInterface.main(args);
            }
        }
    }


}
