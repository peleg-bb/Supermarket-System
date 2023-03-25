package BusinessLayer;

import java.util.List;
import java.util.Map;

public class Schedule {
    private Map<Shift, Map<String, List<Integer>>> shifts; //Map<Shift, Map<role, worker_id>>
    private Map<Shift, List<Integer>> employees_available_shift;
    private Map<Shift, List<Integer>> manager_constraints;
    private String branch;

    public Schedule(Map<Shift, Map<String, List<Integer>>> shifts, Map<Shift, List<Integer>> employees_available_shift, Map<Shift, List<Integer>> manager_constraints, String branch) {
        this.shifts = shifts;
        this.employees_available_shift = employees_available_shift;
        this.manager_constraints = manager_constraints;
        this.branch = branch;
    }

    public Map<Shift, Map<String, List<Integer>>> getShifts() {
        return shifts;
    }

    public Map<Shift, List<Integer>> getEmployees_available_shift() {
        return employees_available_shift;
    }

    public Map<Shift, List<Integer>> getManager_constraints() {
        return manager_constraints;
    }

    public String getBranch() {
        return branch;
    }

    public void add_availability(Shift shift, Integer ID) {
        employees_available_shift.get(shift).add(ID);
    }

    public boolean check_manager_constraint(Shift shift, Integer ID) {
        if (manager_constraints.get(shift).contains(ID)) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean check_employee_availability(Shift shift, Integer ID) {
        return employees_available_shift.get(shift).contains(ID);
    }

    public void assign_shift(Integer ID, Shift shift, String role) {
        shifts.get(shift).get(role).add(ID);
    }

    public boolean is_assigned(Integer ID, Shift shift, String role) {
        return shifts.get(shift).get(role).contains(ID);
    }

    public void remove_shift(Integer ID, Shift shift, String role) {
        shifts.get(shift).get(role).remove(ID);
    }
}
