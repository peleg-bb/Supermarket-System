package BusinessLayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {

    private Branch branch;
    private Worker worker;

    @BeforeEach
    void setUp() {
        branch = new Branch("Be'er Sheva", "Be'er Sheva", 7, 8);
        branch.create_schedule(LocalDate.of(2023, 3, 26));
        worker = new Worker("Ron Hadad", 123456789, 3366998, 30, "Single", true, "Commited to one year", LocalDate.of(2023, 3, 26));
        worker.add_qualified_branch("Be'er Sheva", branch);
    }

    @Test
    void available_to_shift() {
        worker.available_to_shift("2023-03-26", "Be'er Sheva", "MORNING");
        assertEquals(1, branch.get_availability_per_shift(LocalDate.of(2023, 3, 26), Shift.shift_type.MORNING).size());
    }

    @Test
    void add_role() {
        worker.add_role("Storekeeper");
        assertEquals(1, worker.getRoles().size());
    }

    @Test
    void remove_role() {
        add_role();
        worker.remove_role("Storekeeper");
        assertEquals(0, worker.getRoles().size());
    }
}