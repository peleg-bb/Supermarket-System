package BusinessLayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    private Branch branch;
    private Employee employee;

    @BeforeEach
    void setUp() {
        branch = new Branch("Be'er Sheva", "Be'er Sheva", 7, 8);
        branch.create_schedule(LocalDate.of(2023, 3, 26));
        employee = new Employee("Ron Hadad", 123456789, 3366998, 30, "Single", true, "Commited to one year", LocalDate.of(2023, 3, 26));
        employee.add_qualified_branch("Be'er Sheva", branch);
    }

    @Test
    void available_to_shift() {
        employee.available_to_shift("2023-03-26", "Be'er Sheva", "MORNING");
        assertEquals(1, branch.get_availability_per_shift(LocalDate.of(2023, 3, 26), Shift.shift_type.MORNING).size());
    }

    @Test
    void add_role() {
        employee.add_role("Storekeeper");
        assertEquals(1, employee.getRoles().size());
    }

    @Test
    void remove_role() {
        add_role();
        employee.remove_role("Storekeeper");
        assertEquals(0, employee.getRoles().size());
    }
}