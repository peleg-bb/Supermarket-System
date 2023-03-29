package BusinessLayer;

import java.time.LocalDate;
import java.util.*;

public class PersonnelManager extends Worker{
    private Map<String, List<Integer>> roles_employees;
    private Map<Integer, Worker> employees;
    private Map<String, Branch> branches;

    public PersonnelManager(String name, Integer id, Integer bank_account, Integer salary, String family_status, boolean is_student, String terms_of_employment, LocalDate employment_start_date, Map<String, List<Integer>> roles_employees, Map<Integer, Worker> employees, Map<String, Branch> branches) {
        super(name, id, bank_account, salary, family_status, is_student, terms_of_employment, employment_start_date);
        if (roles_employees == null) {
            this.roles_employees = new HashMap<>();
            this.roles_employees.put("Cashier", new LinkedList<>());
            this.roles_employees.put("Storekeeper", new LinkedList<>());
            this.roles_employees.put("Security", new LinkedList<>());
            this.roles_employees.put("Cleaning", new LinkedList<>());
            this.roles_employees.put("Usher", new LinkedList<>());
            this.roles_employees.put("General", new LinkedList<>());
        }
        else {
            this.roles_employees = roles_employees;
        }
        if (employees == null) {
            this.employees = new HashMap<>();
        }
        else {
            this.employees = employees;
        }
        this.branches = branches;
    }

    public boolean assign_to_shift(Integer ID, LocalDate date, Shift.shift_type type, String branch, String role) {
        if (check_employee_availability(ID, date, type, branch, role)) {
            branches.get(branch).assign_to_shift(ID, date, type, role);
            return true;
        }
        return false;
    }

    public boolean remove_shift(Integer ID, LocalDate date, Shift.shift_type type, String branch, String role) {
        if (!branches.get(branch).is_assigned(ID, date, type, role)) {
            return false;
        }
        branches.get(branch).remove_shift(ID, date, type, role);
        return true;
    }

    public boolean add_employee(String name, Integer id, Integer bank_account, Integer salary, String family_status, boolean is_student, String terms_of_employment, LocalDate employment_start_date) {
        if (employees.containsKey(id)) {
            return false;
        }
        Worker employee = new Worker(name, id, bank_account, salary, family_status, is_student, terms_of_employment, employment_start_date);
        employees.put(id, employee);
        return true;
    }

    public boolean remove_employee(Integer id) {
        if (!employees.containsKey(id)) {
            return false;
        }
        employees.remove(id);
        return true;
    }

    public boolean add_employee_role(Integer id, String role) {
        if (roles_employees.get(role).contains(id)) {
            return false;
        }
        else {
            roles_employees.get(role).add(id);
        }
        return employees.get(id).add_role(role);
    }

    public boolean remove_employee_role(Integer id, String role) {
        if (!roles_employees.get(role).contains(id)) {
            return false;
        }
        else {
            roles_employees.get(role).remove(id);
            return employees.get(id).remove_role(role);
        }
    }

    public boolean confirm_shift(LocalDate date, Shift.shift_type type, String branch) {
        return branches.get(branch).confirm_shift(date, type);
    }

    private boolean check_employee_availability(Integer ID, LocalDate date, Shift.shift_type type, String branch, String role) {
        if (branches.get(branch).check_employee_availability(date, type, ID) && check_employee_role(ID, role)) {
            return true;
        }
        return false;
    }

    private boolean check_employee_role(Integer ID, String role) {
        return roles_employees.get(role).contains(ID);
    }

    public void create_schedule(String branch, LocalDate week_first_day) {
        branches.get(branch).create_schedule(week_first_day);
    }

    public boolean create_branch(String branch_name, String location, Integer morning_shift_hours, Integer evening_shift_hours) {
        if (branches.containsKey(branch_name)) {
            return false;
        }
        Branch branch = new Branch(branch_name, location, morning_shift_hours, evening_shift_hours);
        branches.put(branch_name, branch);
        return true;
    }

    public boolean qualify_employee_to_branch(Integer id, String branch) {
        if (!branches.get(branch).assign_worker(id)) {
            return false;
        }
        return employees.get(id).add_qualified_branch(branch, branches.get(branch));
    }

    public boolean assign_shift_manager(LocalDate date, Shift.shift_type type, String branch, Integer id) {
        return branches.get(branch).assign_shift_manager(date, type, id);
    }

    public Map<Integer, Worker> getEmployees() {
        return employees;
    }

    public Map<String, List<Integer>> getRolesEmployees() {
        return roles_employees;
    }

    public Map<String, Branch> getBranches() {
        return branches;
    }
}
