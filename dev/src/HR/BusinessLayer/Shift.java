package HR.BusinessLayer;

import java.sql.Time;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Shift {
    private List<Integer> available_employees;
    private Map<JobType, List<Integer>> employees;
    private List<String> events;
    private List<Integer> manager_constraints;
    private boolean confirmed;
    private Time start;
    private Time end;

    public Shift(Time start, Time end) {
        available_employees = new LinkedList<>();
        employees = new HashMap<>();
        initialize_roles();
        events = new LinkedList<>();
        this.start = start;
        this.end = end;
    }

    public void initialize_roles() {
        for (JobType job: JobType.values()) {
            employees.put(job, new LinkedList<>());
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
        String job = "";
        for (JobType role: employees.keySet()) {
            if (employees.get(role).contains(id)) {
                job = job + role.toString();
            }
        }
        return job;
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
        available_employees.remove(id_num);
        return "";
    }

    public String unassign_shift(int id_num, JobType job) {
        if (!employees.get(job).contains(id_num)) {
            return "Employee isn't assigned to this shift";
        }
        employees.get(job).remove(id_num);
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
        manager_constraints.remove(id_num);
        return "";
    }

    public List<Integer> show_shift_availability() {
        return available_employees;
    }
}
