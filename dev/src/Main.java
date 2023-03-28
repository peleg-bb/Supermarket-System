import BusinessLayer.BranchSchedule;
import BusinessLayer.PersonnelManager;
import BusinessLayer.Schedules;
import BusinessLayer.Worker;

import java.time.LocalDate;

public class Main {

    public void loadData() {
        BranchSchedule schedule1 = new BranchSchedule("Be'er Sheva", LocalDate.of(2023,3,26));
        BranchSchedule schedule2 = new BranchSchedule("Tel Aviv", LocalDate.of(2023,3,26));
        Schedules schedules = new Schedules();
        schedules.add_schedule("Be'er Sheva", schedule1);
        schedules.add_schedule("Tel Aviv", schedule2);
        PersonnelManager manager = new PersonnelManager("Omer Guz", 555555555, 567567, 70, "Married", false, "", LocalDate.of(2023, 1, 1), schedules, null, null);
        manager.add_employee("Ron Hadad", 111111111, 123123, 30, "Single", true, "Commited to one year", LocalDate.of(2023, 1, 22), schedules);
        manager.add_employee("Guy Cohen", 222222222, 234234, 35, "Married", false, "", LocalDate.of(2022, 2, 1), schedules);
        manager.add_employee("Tal Levi", 333333333, 345345, 34, "Single", true, "Can't work on Thursdays", LocalDate.of(2022, 9, 26), schedules);
        manager.add_employee("Ron Zehavi", 444444444, 456456, 33, "Single", true, "Commited to one year", LocalDate.of(2023, 2, 2), schedules);
        manager.add_employee_role(111111111, "Storekeeper");
        manager.add_employee_role(111111111, "Cashier");
        manager.add_employee_role(222222222, "Security");
        manager.add_employee_role(333333333, "General");
    }
}
