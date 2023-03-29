import BusinessLayer.Branch;
import BusinessLayer.BranchSchedule;
import BusinessLayer.PersonnelManager;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public void loadData() {
        Branch Branch1 = new Branch("Be'er Sheva", "Be'er Sheva", 7,8);
        Branch Branch2 = new Branch("Tel Aviv", "Tel Aviv", 6,8);
        Map<String, Branch> branches = new HashMap<>();
        branches.put("Be'er Sheva", Branch1);
        branches.put("Tel Aviv", Branch2);
        PersonnelManager manager = new PersonnelManager("Omer Guz", 555555555, 567567, 70, "Married", false, "", LocalDate.of(2023, 1, 1), null, null, branches);
        manager.add_employee("Ron Hadad", 111111111, 123123, 30, "Single", true, "Commited to one year", LocalDate.of(2023, 1, 22));
        manager.qualify_employee_to_branch(111111111, "Be'er Sheva");
        manager.qualify_employee_to_branch(111111111, "Tel Aviv");
        manager.add_employee("Guy Cohen", 222222222, 234234, 35, "Married", false, "", LocalDate.of(2022, 2, 1));
        manager.qualify_employee_to_branch(222222222, "Be'er Sheva");
        manager.add_employee("Tal Levi", 333333333, 345345, 34, "Single", true, "Can't work on Thursdays", LocalDate.of(2022, 9, 26));
        manager.qualify_employee_to_branch(333333333, "Tel Aviv");
        manager.add_employee("Ron Zehavi", 444444444, 456456, 33, "Single", true, "Commited to one year", LocalDate.of(2023, 2, 2));
        manager.add_employee_role(111111111, "Storekeeper");
        manager.add_employee_role(111111111, "Cashier");
        manager.add_employee_role(222222222, "Security");
        manager.add_employee_role(333333333, "General");
    }
}
