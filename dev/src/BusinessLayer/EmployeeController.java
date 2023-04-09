package BusinessLayer;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeController {
    Map<Integer, Integer> employees_login; //ID, password
    Map<Integer, Employee> employees;

    PersonnelManager manager;

    public EmployeeController(String name, Integer id, Integer bank_account, Integer salary, String family_status, boolean is_student, String terms_of_employment, LocalDate employment_start_date, Integer password) {
        employees_login = new HashMap<>();
        employees = new HashMap<>();
        manager = new PersonnelManager(name, id, bank_account, salary, family_status, is_student, terms_of_employment, employment_start_date);
        employees.put(id, manager);
        employees_login.put(id, password);
    }

    public boolean add_employee(String name, Integer id, Integer bank_account, Integer salary, String family_status, boolean is_student, String terms_of_employment, String employment_start_date, Integer password) {
        LocalDate systemDate = LocalDate.parse(employment_start_date);
        if (employees.containsKey(id)) {
            return false;
        }
        Employee employee = new Employee(name, id, bank_account, salary, family_status, is_student, terms_of_employment, systemDate);
        employees.put(id, employee);
        employees_login.put(id, password);
        return true;
    }

    public boolean remove_employee(Integer id) {
        if (!employees.containsKey(id)) {
            return false;
        }
        employees.remove(id);
        employees_login.remove(id);
        return true;
    }

    public boolean remove_employee_role(Integer id, String role) {
        return employees.get(id).remove_role(role) && manager.remove_employee_role(id, role);
    }

    public boolean add_employee_role(Integer id, String role) {
        return employees.get(id).add_role(role) && manager.add_employee_role(id, role);
    }

    public boolean qualify_employee_to_branch(Integer id, String branch) {
        if (!manager.get_branch(branch).assign_worker(id)) {
            return false;
        }
        return employees.get(id).add_qualified_branch(branch, manager.get_branch(branch));
    }

    public boolean assign_to_shift(Integer id, String date, String type, String branch, String role) {
        return manager.assign_to_shift(id, date, type, branch, role);
    }

    public boolean remove_shift(Integer id, String date, String type, String branch, String role) {
        return manager.remove_shift(id, date, type, branch, role);
    }

    public boolean confirm_shift(String date, String type, String branch) {
        return manager.confirm_shift(date, type, branch);
    }

    public boolean create_schedule(String branch, String week_first_day) {
        return manager.create_schedule(branch, week_first_day);
    }

    public boolean create_branch(String branch_name, String location, Integer morning_shift_hours, Integer evening_shift_hours) {
        return manager.create_branch(branch_name, location, morning_shift_hours, evening_shift_hours);
    }

    public boolean assign_shift_manager(String date, String type, String branch, Integer id) {
        return manager.assign_shift_manager(date, type, branch, id);
    }

    public Map<Integer, Employee> getEmployees() {
        return employees;
    }

    public Map<Employee.role_type, List<Integer>> getRolesEmployees() {
        return manager.getRolesEmployees();
    }

    public Map<String, Branch> getBranches() {
        return manager.getBranches();
    }

    public boolean check_password(int id, int password) {
        if (employees_login.containsKey(id)) {
            if (employees_login.get(id) == password)
            {
                return true;
            }
        }
        return false;
    }

    public boolean add_branch_to_manager(String branch_name, Branch branch) {
        return manager.add_qualified_branch(branch_name, branch);
    }
}
