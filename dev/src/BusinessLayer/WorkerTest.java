package BusinessLayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {

    private BranchSchedule branchSchedule;
    private Worker worker;

    @BeforeEach
    void setUp() {
        branchSchedule = new BranchSchedule("Be'er Sheva", LocalDate.of(2023,3,26));
        Schedules schedules = new Schedules();
        schedules.add_schedule("Be'er Sheva", branchSchedule);
        worker = new Worker("Ron Hadad", 123456789, 3366998, 30, "Single", true, "Commited to one year", LocalDate.of(2023, 3, 26), schedules);
    }

    @Test
    void available_to_shift() {
        worker.available_to_shift(LocalDate.of(2023, 3, 26), "Be'er Sheva", Shift.shift_type.MORNING);
        assertEquals(1, branchSchedule.get_availability_per_shift(LocalDate.of(2023, 3, 26), Shift.shift_type.MORNING).size());
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