package BusinessLayer;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class BranchSchedule {
    private Map<LocalDate, List<Shift>> shifts;
    private Map<Shift, Map<Worker.role_type, List<Integer>>> finished_shifts; //Map<Shift, Map<role, worker_id>>
    private Map<Shift, List<Integer>> employees_available_shift;
    private Map<Shift, List<Integer>> manager_constraints;
    private final String branch;

    public BranchSchedule(String branch, LocalDate weeks_first_date) {
        this.branch = branch;
        initialize_shifts(weeks_first_date);
    }

    private Map<Worker.role_type, List<Integer>> shift_roles() {
        Map<Worker.role_type, List<Integer>> roles = new HashMap<>();
        roles.put(Worker.role_type.Cashier, new LinkedList<>());
        roles.put(Worker.role_type.Storekeeper, new LinkedList<>());
        roles.put(Worker.role_type.Security, new LinkedList<>());
        roles.put(Worker.role_type.Cleaning, new LinkedList<>());
        roles.put(Worker.role_type.Usher, new LinkedList<>());
        roles.put(Worker.role_type.General, new LinkedList<>());
        return roles;
    }

    /* Initializing all of the week's basic data */
    public void initialize_shifts(LocalDate weeks_first_date) {
        shifts = new HashMap<>();
        finished_shifts = new HashMap<>();
        employees_available_shift = new HashMap<>();
        manager_constraints = new HashMap<>();
        Shift sun_morning = new Shift(weeks_first_date, "sun_morning", Shift.shift_type.MORNING, null, new LinkedList<>(), branch);
        finished_shifts.put(sun_morning, shift_roles());
        employees_available_shift.put(sun_morning, new LinkedList<>());
        manager_constraints.put(sun_morning, new LinkedList<>());
        Shift sun_evening = new Shift(weeks_first_date, "sun_evening", Shift.shift_type.EVENING, null, new LinkedList<>(), branch);
        finished_shifts.put(sun_evening, shift_roles());
        employees_available_shift.put(sun_evening, new LinkedList<>());
        manager_constraints.put(sun_evening, new LinkedList<>());
        Shift mon_morning = new Shift(weeks_first_date.plusDays(1), "mon_morning", Shift.shift_type.MORNING, null, new LinkedList<>(), branch);
        finished_shifts.put(mon_morning, shift_roles());
        employees_available_shift.put(mon_morning, new LinkedList<>());
        manager_constraints.put(mon_morning, new LinkedList<>());
        Shift mon_evening = new Shift(weeks_first_date.plusDays(1), "mon_evening", Shift.shift_type.EVENING, null, new LinkedList<>(), branch);
        finished_shifts.put(mon_evening, shift_roles());
        employees_available_shift.put(mon_evening, new LinkedList<>());
        manager_constraints.put(mon_evening, new LinkedList<>());
        Shift tue_morning = new Shift(weeks_first_date.plusDays(2), "tue_morning", Shift.shift_type.MORNING, null, new LinkedList<>(), branch);
        finished_shifts.put(tue_morning, shift_roles());
        employees_available_shift.put(tue_morning, new LinkedList<>());
        manager_constraints.put(tue_morning, new LinkedList<>());
        Shift tue_evening = new Shift(weeks_first_date.plusDays(2), "tue_evening", Shift.shift_type.EVENING, null, new LinkedList<>(), branch);
        finished_shifts.put(tue_evening, shift_roles());
        employees_available_shift.put(tue_evening, new LinkedList<>());
        manager_constraints.put(tue_evening, new LinkedList<>());
        Shift wed_morning = new Shift(weeks_first_date.plusDays(3), "wed_morning", Shift.shift_type.MORNING, null, new LinkedList<>(), branch);
        finished_shifts.put(wed_morning, shift_roles());
        employees_available_shift.put(wed_morning, new LinkedList<>());
        manager_constraints.put(wed_morning, new LinkedList<>());
        Shift wed_evening = new Shift(weeks_first_date.plusDays(3), "wed_evening", Shift.shift_type.EVENING, null, new LinkedList<>(), branch);
        finished_shifts.put(wed_evening, shift_roles());
        employees_available_shift.put(wed_evening, new LinkedList<>());
        manager_constraints.put(wed_evening, new LinkedList<>());
        Shift thu_morning = new Shift(weeks_first_date.plusDays(4), "thu_morning", Shift.shift_type.MORNING, null, new LinkedList<>(), branch);
        finished_shifts.put(thu_morning, shift_roles());
        employees_available_shift.put(thu_morning, new LinkedList<>());
        manager_constraints.put(thu_morning, new LinkedList<>());
        Shift thu_evening = new Shift(weeks_first_date.plusDays(4), "thu_evening", Shift.shift_type.EVENING, null, new LinkedList<>(), branch);
        finished_shifts.put(thu_evening, shift_roles());
        employees_available_shift.put(thu_evening, new LinkedList<>());
        manager_constraints.put(thu_evening, new LinkedList<>());
        Shift fri_morning = new Shift(weeks_first_date.plusDays(5), "fri_morning", Shift.shift_type.MORNING, null, new LinkedList<>(), branch);
        finished_shifts.put(fri_morning, shift_roles());
        employees_available_shift.put(fri_morning, new LinkedList<>());
        manager_constraints.put(fri_morning, new LinkedList<>());
        Shift fri_evening = new Shift(weeks_first_date.plusDays(5), "fri_evening", Shift.shift_type.EVENING, null, new LinkedList<>(), branch);
        finished_shifts.put(fri_evening, shift_roles());
        employees_available_shift.put(fri_evening, new LinkedList<>());
        manager_constraints.put(fri_evening, new LinkedList<>());
        Shift sat_morning = new Shift(weeks_first_date.plusDays(6), "sat_morning", Shift.shift_type.MORNING, null, new LinkedList<>(), branch);
        finished_shifts.put(sat_morning, shift_roles());
        employees_available_shift.put(sat_morning, new LinkedList<>());
        manager_constraints.put(sat_morning, new LinkedList<>());
        Shift sat_evening = new Shift(weeks_first_date.plusDays(6), "sat_evening", Shift.shift_type.EVENING, null, new LinkedList<>(), branch);
        finished_shifts.put(sat_evening, shift_roles());
        employees_available_shift.put(sat_evening, new LinkedList<>());
        manager_constraints.put(sat_evening, new LinkedList<>());
        List<Shift> sun_shifts = new LinkedList<>();
        sun_shifts.add(sun_morning);
        sun_shifts.add(sun_evening);
        List<Shift> mon_shifts = new LinkedList<>();
        mon_shifts.add(mon_morning);
        mon_shifts.add(mon_evening);
        List<Shift> tue_shifts = new LinkedList<>();
        tue_shifts.add(tue_morning);
        tue_shifts.add(tue_evening);
        List<Shift> wed_shifts = new LinkedList<>();
        wed_shifts.add(wed_morning);
        wed_shifts.add(wed_evening);
        List<Shift> thu_shifts = new LinkedList<>();
        thu_shifts.add(thu_morning);
        thu_shifts.add(thu_evening);
        List<Shift> fri_shifts = new LinkedList<>();
        fri_shifts.add(fri_morning);
        fri_shifts.add(fri_evening);
        List<Shift> sat_shifts = new LinkedList<>();
        sat_shifts.add(sat_morning);
        sat_shifts.add(sat_evening);
        shifts.put(weeks_first_date, sun_shifts);
        shifts.put(weeks_first_date.plusDays(1), mon_shifts);
        shifts.put(weeks_first_date.plusDays(2), tue_shifts);
        shifts.put(weeks_first_date.plusDays(3), wed_shifts);
        shifts.put(weeks_first_date.plusDays(4), thu_shifts);
        shifts.put(weeks_first_date.plusDays(5), fri_shifts);
        shifts.put(weeks_first_date.plusDays(6), sat_shifts);
    }

    public void add_availability(LocalDate date, Shift.shift_type type, Integer ID) {
        List<Shift> date_shifts = shifts.get(date);
        for (Shift shift: date_shifts) {
            if (shift.getType() == type) {
                employees_available_shift.get(shift).add(ID);
            }
        }
    }

    public List<Integer> get_availability_per_shift(LocalDate date, Shift.shift_type type) {
        List<Shift> date_shifts = shifts.get(date);
        for (Shift shift: date_shifts) {
            if (shift.getType() == type) {
                return employees_available_shift.get(shift);
            }
        }
        return new LinkedList<>();
    }

    public boolean check_manager_constraint(LocalDate date, Shift.shift_type type, Integer ID) {
        List<Shift> date_shifts = shifts.get(date);
        for (Shift shift: date_shifts) {
            if (shift.getType() == type) {
                return !manager_constraints.get(shift).contains(ID);
            }
        }
        return true;
    }

    public boolean check_employee_availability(LocalDate date, Shift.shift_type type, Integer ID) {
        List<Shift> date_shifts = shifts.get(date);
        for (Shift shift: date_shifts) {
            if (shift.getType() == type) {
                return employees_available_shift.get(shift).contains(ID);
            }
        }
        return false;
    }

    public void assign_shift(Integer ID, LocalDate date, Shift.shift_type type, String role) {
        Worker.role_type Role = Worker.role_type.valueOf(role);
        List<Shift> date_shifts = shifts.get(date);
        for (Shift shift: date_shifts) {
            if (shift.getType() == type) {
                finished_shifts.get(shift).get(Role).add(ID);
            }
        }
    }

    public boolean is_assigned(Integer ID, LocalDate date, Shift.shift_type type, String role) {
        Worker.role_type Role = Worker.role_type.valueOf(role);
        List<Shift> date_shifts = shifts.get(date);
        for (Shift shift: date_shifts) {
            if (shift.getType() == type) {
                return finished_shifts.get(shift).get(Role).contains(ID);
            }
        }
        return false;
    }

    public void remove_shift(Integer ID, LocalDate date, Shift.shift_type type, String role) {
        Worker.role_type Role = Worker.role_type.valueOf(role);
        List<Shift> date_shifts = shifts.get(date);
        for (Shift shift: date_shifts) {
            if (shift.getType() == type) {
                finished_shifts.get(shift).get(Role).remove(ID);
            }
        }
    }

    public boolean confirm_shift(LocalDate date, Shift.shift_type type) {
        List<Shift> date_shifts = shifts.get(date);
        for (Shift shift: date_shifts) {
            if (shift.getType() == type) {
                return shift.getShift_manager() != null;
            }
        }
        return false;
    }

    public boolean assign_shift_manager(LocalDate date, Shift.shift_type type, Integer id) {
        List<Shift> date_shifts = shifts.get(date);
        for (Shift shift: date_shifts) {
            if (shift.getType() == type) {
                shift.setShift_manager(id);
                return true;
            }
        }
        return false;
    }

    public boolean remove_availability(LocalDate date, Shift.shift_type type, Integer id) {
        List<Shift> date_shifts = shifts.get(date);
        for (Shift shift: date_shifts) {
            if (shift.getType() == type) {
                if (employees_available_shift.get(shift).remove(id)) {
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        return false;
    }
}
