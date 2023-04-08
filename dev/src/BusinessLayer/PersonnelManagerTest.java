package BusinessLayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PersonnelManagerTest {
    private Branch branch;
    private PersonnelManager manager;

    @BeforeEach
    void setUp() {
        branch = new Branch("Be'er Sheva", "Be'er Sheva", 7,8);
        branch.create_schedule(LocalDate.of(2023,3,26));
        Map<String, Branch> branches = new HashMap<>();
        branches.put("Be'er Sheva", branch);
        manager = new PersonnelManager("Ron Hadad", 123456789, 3366998, 30, "Single", true, "Commited to one year", LocalDate.of(2023, 1, 26), null, null);
    }
    @Test
    void assign_to_shift() {
        manager.add_employee("Guy Cohen", 666666666, 1231231, 30, "Single", false, "", "2023-01-26");
        manager.add_employee_role(666666666, "Storekeeper");
        manager.assign_to_shift(666666666, "2023-03-27", "MORNING", "Be'er Sheva", "Storekeeper");
        manager.qualify_employee_to_branch(666666666, "Be'er Sheva");
        boolean is_assigned = branch.getSchedule().is_assigned(666666666, LocalDate.of(2023,3,27), Shift.shift_type.MORNING, "Storekeeper");
        assertFalse(is_assigned);
        manager.getEmployees().get(666666666).available_to_shift("2023-03-27","Be'er Sheva", "MORNING");
        manager.assign_to_shift(666666666, "2023-03-27", "MORNING", "Be'er Sheva", "Storekeeper");
        is_assigned = branch.getSchedule().is_assigned(666666666, LocalDate.of(2023,3,27), Shift.shift_type.MORNING, "Storekeeper");
        assertTrue(is_assigned);
    }

    @Test
    void remove_shift() {
        manager.add_employee("Guy Cohen", 666666666, 1231231, 30, "Single", false, "", "2023-01-26");
        manager.add_employee_role(666666666, "Storekeeper");
        manager.getEmployees().get(666666666).available_to_shift("2023-03-27","Be'er Sheva", "MORNING");
        manager.assign_to_shift(666666666, "2023-03-27", "MORNING", "Be'er Sheva", "Storekeeper");
        manager.remove_shift(666666666, "2023-03-27", "MORNING", "Be'er Sheva", "Storekeeper");
        boolean is_assigned = branch.getSchedule().is_assigned(666666666, LocalDate.of(2023,3,27), Shift.shift_type.MORNING, "Storekeeper");
        assertFalse(is_assigned);
    }

    @Test
    void add_employee() {
        boolean added = manager.add_employee("Guy Cohen", 666666666, 1231231, 30, "Single", false, "", "2023-01-26");
        assertEquals(1, manager.getEmployees().size());
        assertTrue(added);
        added = manager.add_employee("Guy Cohen", 666666666, 1231231, 30, "Single", false, "", "2023-01-26");
        assertFalse(added);
    }

    @Test
    void remove_employee() {
        manager.add_employee("Guy Cohen", 666666666, 1231231, 30, "Single", false, "", "2023-01-26");
        manager.remove_employee(666656666);
        assertEquals(1, manager.getEmployees().size());
        manager.remove_employee(666666666);
        assertEquals(0, manager.getEmployees().size());
    }

    @Test
    void add_employee_role() {
        manager.add_employee("Guy Cohen", 666666666, 1231231, 30, "Single", false, "", "2023-01-26");
        manager.add_employee_role(666666666, "Storekeeper");
        boolean test1 = manager.getRolesEmployees().get(Employee.role_type.Storekeeper).contains(666666666);
        assertTrue(test1);
        boolean test2 = manager.getEmployees().get(666666666).getRoles().contains(Employee.role_type.Storekeeper);
        assertTrue(test2);
    }

    @Test
    void remove_employee_role() {
        manager.add_employee("Guy Cohen", 666666666, 1231231, 30, "Single", false, "", "2023-01-26");
        manager.add_employee_role(666666666, "Storekeeper");
        boolean test1 = manager.getRolesEmployees().get(Employee.role_type.Storekeeper).contains(666666666);
        assertTrue(test1);
        boolean test2 = manager.getEmployees().get(666666666).getRoles().contains(Employee.role_type.Storekeeper);
        assertTrue(test2);
        manager.remove_employee_role(666666666, "Storekeeper");
        boolean test3 = manager.getRolesEmployees().get(Employee.role_type.Storekeeper).contains(666666666);
        assertFalse(test3);
        boolean test4 = manager.getEmployees().get(666666666).getRoles().contains(Employee.role_type.Storekeeper);
        assertFalse(test4);

    }

    @Test
    void confirm_shift() {
        boolean test1 = manager.confirm_shift("2023-03-27", "MORNING", "Be'er Sheva");
        assertFalse(test1);
        manager.assign_shift_manager("2023-03-27", "MORNING", "Be'er Sheva", 666666666);
        test1 = manager.confirm_shift("2023-03-27", "MORNING", "Be'er Sheva");
        assertTrue(test1);
    }

    @Test
    void assign_shift_manager() {
        boolean test1 = manager.assign_shift_manager("2023-03-27", "MORNING", "Be'er Sheva", 666666666);
        assertTrue(test1);
        boolean test2 = manager.confirm_shift("2023-03-27", "MORNING", "Be'er Sheva");
        assertTrue(test2);
    }

    @Test
    void create_branch() {
        boolean test1 = manager.getBranches().containsKey("Tel Aviv");
        assertFalse(test1);
        manager.create_branch("Tel Aviv", "Tel Aviv", 7,8);
        test1 = manager.getBranches().containsKey("Tel Aviv");
        assertTrue(test1);
    }
}