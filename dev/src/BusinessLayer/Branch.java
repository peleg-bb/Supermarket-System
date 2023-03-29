package BusinessLayer;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Branch {
    private String name;
    private BranchSchedule branchSchedule;
    private List<Integer> workers;
    private String location;
    private Integer morning_shift_hours;
    private Integer evening_shift_hours;

    public Branch(String name, String location, Integer morning_shift_hours, Integer evening_shift_hours) {
        branchSchedule = new BranchSchedule(name);
        workers = new LinkedList<>();
        this.location = location;
        this.morning_shift_hours = morning_shift_hours;
        this.evening_shift_hours = evening_shift_hours;
    }

    public boolean assign_shift_manager(LocalDate date, Shift.shift_type type, Integer id) {
        return branchSchedule.assign_shift_manager(date, type, id);
    }

    public BranchSchedule getSchedule() {
        return branchSchedule;
    }

    public void create_schedule(LocalDate week_first_day) {
        branchSchedule.initialize_shifts(week_first_day);
    }

    public boolean assign_worker(Integer id) {
        if (workers.contains(id)) {
            return false;
        }
        workers.add(id);
        return true;
    }

    public void assign_to_shift(Integer ID, LocalDate date, Shift.shift_type type, String role) {
        branchSchedule.assign_shift(ID, date, type, role);
    }

    public boolean is_assigned(Integer id, LocalDate date, Shift.shift_type type, String role) {
        return branchSchedule.is_assigned(id, date, type, role);
    }

    public void remove_shift(Integer id, LocalDate date, Shift.shift_type type, String role) {
        branchSchedule.remove_shift(id, date, type, role);
    }

    public boolean confirm_shift(LocalDate date, Shift.shift_type type) {
        return branchSchedule.confirm_shift(date, type);
    }

    public boolean check_employee_availability(LocalDate date, Shift.shift_type type, Integer id) {
        return branchSchedule.check_employee_availability(date, type, id);
    }

    public List<Integer> get_availability_per_shift(LocalDate date, Shift.shift_type type) {
        return branchSchedule.get_availability_per_shift(date, type);
    }

    public void add_availability(LocalDate date, Shift.shift_type type, Integer id) {
        branchSchedule.add_availability(date, type, id);
    }
}
