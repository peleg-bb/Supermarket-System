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

    private void load_hr() {
        employeeCLI.add_hr(111111111, "Tomer Naydnov", 12345678, 70, "None", LocalDate.now(), FamilyStatus.SINGLE, true, "123456");
    }

    public static void main(String[] args) throws SQLException {
        Connect.getInstance().deleteRecordsOfTables();
        Menu m = new Menu();
        m.load_hr();
        m.run();
    }
}
