import Deliveries.PresentationLayer.CLI.CLI;
import Deliveries.PresentationLayer.GUI.View.MainMenuFrame;
import HR.PresentationLayer.Menu;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class Deliveries_HR_Main {

    public static void main(String[] args) throws SQLException {
        if (Objects.equals(args[0], "GUI")) {
            SwingUtilities.invokeLater(MainMenuFrame::new);
        }
        else {
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


}
