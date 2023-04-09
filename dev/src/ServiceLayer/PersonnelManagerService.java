package ServiceLayer;

import BusinessLayer.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class PersonnelManagerService {
    EmployeeController employeeController;

    public PersonnelManagerService(String name, Integer id, Integer bank_account, Integer salary, String family_status, boolean is_student, String terms_of_employment, LocalDate employment_start_date, Integer password) {
        employeeController = new EmployeeController(name, id, bank_account, salary, family_status, is_student, terms_of_employment, employment_start_date, password);
    }

    public boolean assign_to_shift(Integer ID, String date, String type, String branch, String role) {
        return employeeController.assign_to_shift(ID, date, type, branch, role);
    }

    public boolean remove_shift(Integer ID, String date, String type, String branch, String role) {
        return employeeController.remove_shift(ID, date, type, branch, role);
    }

    public boolean add_employee(String name, Integer id, Integer bank_account, Integer salary, String family_status, boolean is_student, String terms_of_employment, String employment_start_date, Integer password) {
        return employeeController.add_employee(name, id, bank_account, salary, family_status, is_student, terms_of_employment, employment_start_date, password);
    }

    public boolean remove_employee(Integer id) {
        return employeeController.remove_employee(id);
    }

    public boolean add_employee_role(Integer id, String role) {
        return employeeController.add_employee_role(id, role);
    }

    public boolean remove_employee_role(Integer id, String role) {
        return employeeController.remove_employee_role(id, role);
    }

    public boolean confirm_shift(String date, String type, String branch) {
        return employeeController.confirm_shift(date, type, branch);
    }

    public boolean create_schedule(String branch, String week_first_day) {
        return employeeController.create_schedule(branch, week_first_day);
    }

    public boolean create_branch(String branch_name, String location, Integer morning_shift_hours, Integer evening_shift_hours) {
        return employeeController.create_branch(branch_name, location, morning_shift_hours, evening_shift_hours);
    }

    public boolean qualify_employee_to_branch(Integer id, String branch) {
        return employeeController.qualify_employee_to_branch(id, branch);
    }

    public boolean assign_shift_manager(String date, String type, String branch, Integer id) {
        return employeeController.assign_shift_manager(date, type, branch, id);
    }

    public Employee getEmployee(int id) {
        return employeeController.getEmployees().get(id);
    }

    public boolean check_password(int id, int password) {
        return employeeController.check_password(id, password);
    }
}
