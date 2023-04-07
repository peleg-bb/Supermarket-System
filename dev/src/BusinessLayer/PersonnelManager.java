package BusinessLayer;

import java.time.LocalDate;
import java.util.*;

public class PersonnelManager extends Worker{
    private Map<Worker.role_type, List<Integer>> roles_employees;
    private Map<Integer, Worker> employees;
    private Map<String, Branch> branches;

    public PersonnelManager(String name, Integer id, Integer bank_account, Integer salary, String family_status, boolean is_student, String terms_of_employment, LocalDate employment_start_date, Map<Worker.role_type, List<Integer>> roles_employees, Map<Integer, Worker> employees, Map<String, Branch> branches) {
        super(name, id, bank_account, salary, family_status, is_student, terms_of_employment, employment_start_date);
        if (roles_employees == null) {
            this.roles_employees = new HashMap<>();
            this.roles_employees.put(Worker.role_type.Cashier, new LinkedList<>());
            this.roles_employees.put(Worker.role_type.Storekeeper, new LinkedList<>());
            this.roles_employees.put(Worker.role_type.Security, new LinkedList<>());
            this.roles_employees.put(Worker.role_type.Cleaning, new LinkedList<>());
            this.roles_employees.put(Worker.role_type.Usher, new LinkedList<>());
            this.roles_employees.put(Worker.role_type.General, new LinkedList<>());
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

    /** Assigns an employee to a shift - in a specific branch **/
    public boolean assign_to_shift(Integer ID, String date, String type, String branch, String role) {
        LocalDate systemDate = LocalDate.parse(date);
        Shift.shift_type shift_type = Shift.shift_type.valueOf(type);
        if (check_employee_availability(ID, systemDate, shift_type, branch, role)) {
            branches.get(branch).assign_to_shift(ID, systemDate, shift_type, role);
            return true;
        }
        return false;
    }

    /** Removes an employee from a shift - in a specific branch **/
    public boolean remove_shift(Integer ID, String date, String type, String branch, String role) {
        LocalDate systemDate = LocalDate.parse(date);
        Shift.shift_type shift_type = Shift.shift_type.valueOf(type);
        if (!branches.get(branch).is_assigned(ID, systemDate, shift_type, role)) {
            return false;
        }
        branches.get(branch).remove_shift(ID, systemDate, shift_type, role);
        return true;
    }

    /** Adds an employee to the system **/
    public boolean add_employee(String name, Integer id, Integer bank_account, Integer salary, String family_status, boolean is_student, String terms_of_employment, LocalDate employment_start_date) {
        if (employees.containsKey(id)) {
            return false;
        }
        Worker employee = new Worker(name, id, bank_account, salary, family_status, is_student, terms_of_employment, employment_start_date);
        employees.put(id, employee);
        return true;
    }

    /** Removes an employee from the system **/
    public boolean remove_employee(Integer id) {
        if (!employees.containsKey(id)) {
            return false;
        }
        employees.remove(id);
        return true;
    }

    /** Qualifies an employee to a role **/
    public boolean add_employee_role(Integer id, String role) {
        Worker.role_type Role = Worker.role_type.valueOf(role);
        if (roles_employees.get(Role).contains(id)) {
            return false;
        }
        else {
            roles_employees.get(Role).add(id);
        }
        return employees.get(id).add_role(role);
    }

    /** Removes employee's role qualification **/
    public boolean remove_employee_role(Integer id, String role) {
        Worker.role_type Role = Worker.role_type.valueOf(role);
        if (!roles_employees.get(Role).contains(id)) {
            return false;
        }
        else {
            roles_employees.get(Role).remove(id);
            return employees.get(id).remove_role(role);
        }
    }

    /** Confirming a shift - making sure it has a shift manager **/
    public boolean confirm_shift(String date, String type, String branch) {
        LocalDate systemDate = LocalDate.parse(date);
        Shift.shift_type shift_type = Shift.shift_type.valueOf(type);
        return branches.get(branch).confirm_shift(systemDate, shift_type);
    }

    /** Checks whether the employee is available to work in a shift **/
    private boolean check_employee_availability(Integer ID, LocalDate date, Shift.shift_type type, String branch, String role) {
        return branches.get(branch).check_employee_availability(date, type, ID) && check_employee_role(ID, role);
    }

    /** Checks whether the employee is qualified to a role **/
    private boolean check_employee_role(Integer ID, String role) {
        Worker.role_type Role = Worker.role_type.valueOf(role);
        return roles_employees.get(Role).contains(ID);
    }

    /** Creates a weekly schedule for a specific branch, starting from sunday - till saturday **/
    public boolean create_schedule(String branch, String week_first_day) {
        LocalDate systemDate = LocalDate.parse(week_first_day);
        if (!branches.containsKey(branch)) {
            return false;
        }
        branches.get(branch).create_schedule(systemDate);
        return true;
    }

    /** Creates a branch **/
    public boolean create_branch(String branch_name, String location, Integer morning_shift_hours, Integer evening_shift_hours) {
        if (branches.containsKey(branch_name)) {
            return false;
        }
        Branch branch = new Branch(branch_name, location, morning_shift_hours, evening_shift_hours);
        branches.put(branch_name, branch);
        return true;
    }

    /** Qualifies an employee to work in a branch **/
    public boolean qualify_employee_to_branch(Integer id, String branch) {
        if (!branches.get(branch).assign_worker(id)) {
            return false;
        }
        return employees.get(id).add_qualified_branch(branch, branches.get(branch));
    }

    /** Assigns a shift manager to a shift **/
    public boolean assign_shift_manager(String date, String type, String branch, Integer id) {
        LocalDate systemDate = LocalDate.parse(date);
        Shift.shift_type shift_type = Shift.shift_type.valueOf(type);
        return branches.get(branch).assign_shift_manager(systemDate, shift_type, id);
    }

    public Map<Integer, Worker> getEmployees() {
        return employees;
    }

    public Map<Worker.role_type, List<Integer>> getRolesEmployees() {
        return roles_employees;
    }

    public Map<String, Branch> getBranches() {
        return branches;
    }
}
