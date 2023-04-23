package HR.PresentationLayer;

import HR.DataAccessLayer.Connect;

import java.sql.SQLException;
import java.util.Date;

public class Menu {
    private final HRMenu employeeCLI = new HRMenu();

    public void run() {
        employeeCLI.run();
    }

    private void load_hr() {
        employeeCLI.add_hr(111111111, "Tomer Naydnov", 12345678, 70, "None", new Date(), "Single", true, "123456");
    }

    public static void main(String[] args) throws SQLException {
        Connect.getInstance().deleteRecordsOfTables();
        Menu m = new Menu();
        m.load_hr();
        m.run();
    }
}
