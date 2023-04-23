package HR.BusinessLayer;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

public class Shift {
    private final String store;
    private List<Integer> available_employees;
    private Map<JobType, List<Integer>> employees;
    private Map<Integer, Integer> product_canceling; //Employee, product_id
    private List<Integer> manager_constraints;
    private boolean confirmed;
    private final Time start;
    private final Time end;
    public Shift(String store, Time start, Time end) {
        available_employees = new LinkedList<>();
        employees = new HashMap<>();
        initialize_roles();
        product_canceling = new HashMap<>();
        this.start = start;
        this.end = end;
        confirmed = false;
        this.store = store;
        manager_constraints = new LinkedList<>();
    }

    public void initialize_roles() {
        for (JobType job: JobType.values()) {
            if (!employees.containsKey(job)) {
                employees.put(job, new LinkedList<>());
            }
        }
    }

    public String add_availability(Integer id) {
        if (available_employees.contains(id)) {
            return "Already available on this shift";
        }
        if (manager_constraints.contains(id)) {
            return "Can't work in this shift";
        }
        available_employees.add(id);
        return "";
    }

    public String remove_availability(Integer id) {
        if (!available_employees.contains(id)) {
            return "You're not available on this shift";
        }
        available_employees.remove(id);
        return "";
    }

    public boolean is_available(Integer id) {
        return available_employees.contains(id);
    }

    public String is_assigned(Integer id) {
        StringBuilder job = new StringBuilder();
        for (JobType role: employees.keySet()) {
            if (employees.get(role).contains(id)) {
                job.append(role.toString());
            }
        }
        return job.toString();
    }

    public String confirm_shift() {
        if (employees.get(JobType.SHIFTMANAGER).isEmpty()) {
            return "There is no shift manager";
        }
        confirmed = true;
        return "";
    }

    public String assign_shift(int id_num, JobType role) {
        if (!available_employees.contains(id_num)) {
            return "Employee isn't available on this shift";
        }
        employees.get(role).add(id_num);
        return "";
    }

    public String unassign_shift(int id_num, JobType job) {
        if (!employees.get(job).contains(id_num)) {
            return "Employee isn't assigned to this shift";
        }
        employees.get(job).remove(Integer.valueOf(id_num));
        return "";
    }

    public String limit_work(int id_num) {
        if (!manager_constraints.contains(id_num)) {
            return "User already limited on this shift";
        }
        manager_constraints.add(id_num);
        return "";
    }

    public String remove_worker_limit(int id_num) {
        if (!manager_constraints.contains(id_num)) {
            return "User isn't limited on this shift";
        }
        manager_constraints.remove(Integer.valueOf(id_num));
        return "";
    }

    public List<Integer> show_shift_availability() {
        return available_employees;
    }

    public double get_length() {
        LocalTime startLocalTime = start.toLocalTime();
        LocalTime endLocalTime = end.toLocalTime();

        Duration duration = Duration.between(startLocalTime, endLocalTime);
        return duration.toHours();
    }

    public Date get_start() {
        return start;
    }

    public Date get_end() {
        return end;
    }

    public boolean check_availability() {
        return !employees.get(JobType.STOREKEEPER).isEmpty();
    }

    public List<String> get_available_drivers() {
        List<String> drivers_ids = new LinkedList<>();
        for (Integer id: available_employees) {
            drivers_ids.add(id.toString());
        }
        return drivers_ids;
    }

    public boolean is_assigned_to_role(int id_num, JobType role) {
        return employees.get(role).contains(id_num);
    }

    public void set_available(List<Integer> employees) {
        available_employees = employees;
    }
    public void set_employees(Map<JobType, List<Integer>> employees) {
        this.employees = employees;
    }
    public void set_events(Map<Integer, Integer> events) {
        this.product_canceling = events;
    }
    public void set_constraints(List<Integer> constraints) {
        manager_constraints = constraints;
    }

    public String get_store() {
        return store;
    }

    public String cancel_product(int id, int product_id_num) {
        product_canceling.put(id, product_id_num);
        return "";
    }

    public boolean is_confirmed() {
        return confirmed;
    }

    public Map<JobType, List<Integer>> show_shift_assigned() {
        return employees;
    }

    public boolean is_limited(int id) {
        return manager_constraints.contains(id);
    }
}
