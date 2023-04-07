package BusinessLayer;
import java.time.LocalDate;
import java.util.*;


public class Worker {
    protected String name;
    protected Integer id;
    protected Integer bank_account;
    protected Integer salary;
    protected String family_status;
    protected boolean is_student;
    protected String terms_of_employment;
    protected LocalDate employment_start_date;
    protected Map<String, Branch> qualified_branches;
    protected List<role_type> roles;
    public enum role_type {
        Cashier,
        Storekeeper,
        Security,
        Cleaning,
        Usher,
        General
    }

    public Worker(String name, Integer id, Integer bank_account, Integer salary, String family_status, boolean is_student, String terms_of_employment, LocalDate employment_start_date) {
        this.name = name;
        this.id = id;
        this.bank_account = bank_account;
        this.salary = salary;
        this.family_status = family_status;
        this.is_student = is_student;
        this.terms_of_employment = terms_of_employment;
        this.employment_start_date = employment_start_date;
        roles = new LinkedList<>();
        qualified_branches = new HashMap<>();
    }

    /** Adds availability to a specific shift - in a specific branch **/
    public boolean available_to_shift(String date, String branch, String type) {
        LocalDate systemDate = LocalDate.parse(date);
        Shift.shift_type shift_type = Shift.shift_type.valueOf(type);
        if (qualified_branches.containsKey(branch) && check_manager_constraint(systemDate, branch, shift_type)) {
            qualified_branches.get(branch).add_availability(systemDate, shift_type, this.id);
            return true;
        }
        else {
            return false;
        }
    }

    /** Removes availability from a specific shift - in a specific branch **/
    public boolean remove_availability(String date, String branch, String type) {
        LocalDate systemDate = LocalDate.parse(date);
        Shift.shift_type shift_type = Shift.shift_type.valueOf(type);
        if (qualified_branches.containsKey(branch)) {
            return qualified_branches.get(branch).remove_availability(systemDate, shift_type, this.id);
        }
        else {
            return false;
        }
    }

    /** Adds the branch to the list of qualified branches to work at **/
    public boolean add_qualified_branch(String branch_name, Branch branch) {
        if (qualified_branches.containsKey(branch_name)) {
            return false;
        }
        else {
            qualified_branches.put(branch_name, branch);
            return true;
        }
    }

    /** Checks whether the manager has restricted the work of the worker **/
    private boolean check_manager_constraint(LocalDate date, String branch, Shift.shift_type type) {
        return qualified_branches.get(branch).getSchedule().check_manager_constraint(date, type, this.id);
    }

    /** Adds the role to the list of qualified roles of the worker **/
    public boolean add_role(String role) {
        role_type Role = role_type.valueOf(role);
        if (roles.contains(Role)) {
            return false;
        }
        roles.add(Role);
        return true;
    }

    /** Removes the role from the list of qualified roles of the worker **/
    public boolean remove_role(String role) {
        role_type Role = role_type.valueOf(role);
        if (!roles.contains(Role)) {
            return false;
        }
        roles.remove(Role);
        return true;
    }

    public List<role_type> getRoles() {
        return roles;
    }

    public int getID() {
        return id;
    }
}
