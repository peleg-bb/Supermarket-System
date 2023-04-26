package HR.PresentationLayer;

import HR.BusinessLayer.FamilyStatus;
import HR.DataAccessLayer.Connect;

import java.sql.SQLException;
import java.time.LocalDate;

public class Menu {
    private final HRMenu employeeCLI = new HRMenu();

    public void run() {
        employeeCLI.run();
    }

    private void load_data_example() {
        employeeCLI.load_data_example();
    }

    public static void main(String[] args) throws SQLException {
        //Connect.getInstance().deleteRecordsOfTables();
        Menu m = new Menu();
        m.load_data_example();
        m.run();
    }
}
