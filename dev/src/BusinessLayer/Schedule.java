package BusinessLayer;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Schedule {
    private Map<LocalDate, List<Shift>> shifts;
    private Map<Shift, Map<String, List<Integer>>> finished_shifts; //Map<Shift, Map<role, worker_id>>
    private Map<Shift, List<Integer>> employees_available_shift;
    private Map<Shift, List<Integer>> manager_constraints;
    private String branch;

    public Schedule(Map<Shift, Map<String, List<Integer>>> finished_shifts, Map<Shift, List<Integer>> employees_available_shift, Map<Shift, List<Integer>> manager_constraints, String branch) {
        this.finished_shifts = finished_shifts;
        this.employees_available_shift = employees_available_shift;
        this.manager_constraints = manager_constraints;
        this.branch = branch;
    }

    public Map<Shift, Map<String, List<Integer>>> getShifts() {
        return finished_shifts;
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

    public void add_availability(LocalDate date, Shift.shift_type type, Integer ID) {
        List<Shift> date_shifts = shifts.get(date);
        for (Shift shift: date_shifts) {
            if (shift.getType() == type) {
                employees_available_shift.get(shift).add(ID);
            }
        }
    }

    public boolean check_manager_constraint(LocalDate date, Shift.shift_type type, Integer ID) {
        List<Shift> date_shifts = shifts.get(date);
        for (Shift shift: date_shifts) {
            if (shift.getType() == type) {
                if (manager_constraints.get(shift).contains(ID)) {
                    return false;
                }
                else {
                    return true;
                }
            }
        }
        return true;
    }

//    public boolean check_employee_availability(Shift shift, Integer ID) {
//        return employees_available_shift.get(shift).contains(ID);
//    }

    public boolean check_employee_availability(LocalDate date, Shift.shift_type type, Integer ID) {
        List<Shift> date_shifts = shifts.get(date);
        for (Shift shift: date_shifts) {
            if (shift.getType() == type) {
                return employees_available_shift.get(shift).contains(ID);
            }
        }
        return false;
    }

//    public void assign_shift(Integer ID, Shift shift, String role) {
//        finished_shifts.get(shift).get(role).add(ID);
//    }

    public void assign_shift(Integer ID, LocalDate date, Shift.shift_type type, String role) {
        List<Shift> date_shifts = shifts.get(date);
        for (Shift shift: date_shifts) {
            if (shift.getType() == type) {
                finished_shifts.get(shift).get(role).add(ID);
            }
        }
    }

//    public boolean is_assigned(Integer ID, Shift shift, String role) {
//        return finished_shifts.get(shift).get(role).contains(ID);
//    }

    public boolean is_assigned(Integer ID, LocalDate date, Shift.shift_type type, String role) {
        List<Shift> date_shifts = shifts.get(date);
        for (Shift shift: date_shifts) {
            if (shift.getType() == type) {
                return finished_shifts.get(shift).get(role).contains(ID);
            }
        }
        return false;
    }

    public void remove_shift(Integer ID, LocalDate date, Shift.shift_type type, String role) {
        List<Shift> date_shifts = shifts.get(date);
        for (Shift shift: date_shifts) {
            if (shift.getType() == type) {
                finished_shifts.get(shift).get(role).remove(ID);
            }
        }
    }

    public boolean confirm_shift(LocalDate date, Shift.shift_type type) {
        List<Shift> date_shifts = shifts.get(date);
        for (Shift shift: date_shifts) {
            if (shift.getType() == type) {
                if (shift.getShift_manager() != null) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
