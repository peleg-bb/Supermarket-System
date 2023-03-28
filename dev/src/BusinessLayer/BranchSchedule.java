package BusinessLayer;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class BranchSchedule {
    private Map<LocalDate, List<Shift>> shifts;
    private Map<Shift, Map<String, List<Integer>>> finished_shifts; //Map<Shift, Map<role, worker_id>>
    private Map<Shift, List<Integer>> employees_available_shift;
    private Map<Shift, List<Integer>> manager_constraints;
    private String branch;

    public BranchSchedule(String branch, LocalDate week_first_date) {
        this.branch = branch;
        initialize_shifts(week_first_date);
    }

    private Map<String, List<Integer>> shift_roles() {
        Map<String, List<Integer>> roles = new HashMap<>();
        roles.put("Cashier", new LinkedList<>());
        roles.put("Storekeeper", new LinkedList<>());
        roles.put("Security", new LinkedList<>());
        roles.put("Cleaning", new LinkedList<>());
        roles.put("Usher", new LinkedList<>());
        roles.put("General", new LinkedList<>());
        return roles;
    }

    /* Initializing all of the week's basic data */
    private void initialize_shifts(LocalDate weeks_first_date) {
        shifts = new HashMap<>();
        finished_shifts = new HashMap<>();
        employees_available_shift = new HashMap<>();
        manager_constraints = new HashMap<>();
        Shift sun_morning = new Shift(weeks_first_date, "sun_morning", Shift.shift_type.MORNING, null, new LinkedList<>(), "Be'er Sheva");
        finished_shifts.put(sun_morning, shift_roles());
        employees_available_shift.put(sun_morning, new LinkedList<>());
        manager_constraints.put(sun_morning, new LinkedList<>());
        Shift sun_evening = new Shift(weeks_first_date, "sun_evening", Shift.shift_type.EVENING, null, new LinkedList<>(), "Be'er Sheva");
        finished_shifts.put(sun_evening, shift_roles());
        employees_available_shift.put(sun_evening, new LinkedList<>());
        manager_constraints.put(sun_evening, new LinkedList<>());
        Shift mon_morning = new Shift(weeks_first_date.plusDays(1), "mon_morning", Shift.shift_type.MORNING, null, new LinkedList<>(), "Be'er Sheva");
        finished_shifts.put(mon_morning, shift_roles());
        employees_available_shift.put(mon_morning, new LinkedList<>());
        manager_constraints.put(mon_morning, new LinkedList<>());
        Shift mon_evening = new Shift(weeks_first_date.plusDays(1), "mon_evening", Shift.shift_type.EVENING, null, new LinkedList<>(), "Be'er Sheva");
        finished_shifts.put(mon_evening, shift_roles());
        employees_available_shift.put(mon_evening, new LinkedList<>());
        manager_constraints.put(mon_evening, new LinkedList<>());
        Shift tue_morning = new Shift(weeks_first_date.plusDays(2), "tue_morning", Shift.shift_type.MORNING, null, new LinkedList<>(), "Be'er Sheva");
        finished_shifts.put(tue_morning, shift_roles());
        employees_available_shift.put(tue_morning, new LinkedList<>());
        manager_constraints.put(tue_morning, new LinkedList<>());
        Shift tue_evening = new Shift(weeks_first_date.plusDays(2), "tue_evening", Shift.shift_type.EVENING, null, new LinkedList<>(), "Be'er Sheva");
        finished_shifts.put(tue_evening, shift_roles());
        employees_available_shift.put(tue_evening, new LinkedList<>());
        manager_constraints.put(tue_evening, new LinkedList<>());
        Shift wed_morning = new Shift(weeks_first_date.plusDays(3), "wed_morning", Shift.shift_type.MORNING, null, new LinkedList<>(), "Be'er Sheva");
        finished_shifts.put(wed_morning, shift_roles());
        employees_available_shift.put(wed_morning, new LinkedList<>());
        manager_constraints.put(wed_morning, new LinkedList<>());
        Shift wed_evening = new Shift(weeks_first_date.plusDays(3), "wed_evening", Shift.shift_type.EVENING, null, new LinkedList<>(), "Be'er Sheva");
        finished_shifts.put(wed_evening, shift_roles());
        employees_available_shift.put(wed_evening, new LinkedList<>());
        manager_constraints.put(wed_evening, new LinkedList<>());
        Shift thu_morning = new Shift(weeks_first_date.plusDays(4), "thu_morning", Shift.shift_type.MORNING, null, new LinkedList<>(), "Be'er Sheva");
        finished_shifts.put(thu_morning, shift_roles());
        employees_available_shift.put(thu_morning, new LinkedList<>());
        manager_constraints.put(thu_morning, new LinkedList<>());
        Shift thu_evening = new Shift(weeks_first_date.plusDays(4), "thu_evening", Shift.shift_type.EVENING, null, new LinkedList<>(), "Be'er Sheva");
        finished_shifts.put(thu_evening, shift_roles());
        employees_available_shift.put(thu_evening, new LinkedList<>());
        manager_constraints.put(thu_evening, new LinkedList<>());
        List<Shift> sun_shifts = new LinkedList<Shift>();
        sun_shifts.add(sun_morning);
        sun_shifts.add(sun_evening);
        List<Shift> mon_shifts = new LinkedList<Shift>();
        mon_shifts.add(mon_morning);
        mon_shifts.add(mon_evening);
        List<Shift> tue_shifts = new LinkedList<Shift>();
        tue_shifts.add(tue_morning);
        tue_shifts.add(tue_evening);
        List<Shift> wed_shifts = new LinkedList<Shift>();
        wed_shifts.add(wed_morning);
        wed_shifts.add(wed_evening);
        List<Shift> thu_shifts = new LinkedList<Shift>();
        thu_shifts.add(thu_morning);
        thu_shifts.add(thu_evening);
        shifts.put(weeks_first_date, sun_shifts);
        shifts.put(weeks_first_date.plusDays(1), mon_shifts);
        shifts.put(weeks_first_date.plusDays(2), tue_shifts);
        shifts.put(weeks_first_date.plusDays(3), wed_shifts);
        shifts.put(weeks_first_date.plusDays(4), thu_shifts);
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
        List<Shift> date_shifts = shifts.get(date);
        for (Shift shift: date_shifts) {
            if (shift.getType() == type) {
                finished_shifts.get(shift).get(role).add(ID);
            }
        }
    }

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
}
