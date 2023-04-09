package ServiceLayer;

import BusinessLayer.Employee;

import java.time.LocalDate;
import java.util.Map;

public class EmployeeService {
    Employee employee;

    public EmployeeService(String name, Integer id, Integer bank_account, Integer salary, String family_status, boolean is_student, String terms_of_employment, LocalDate employment_start_date) {
        employee = new Employee(name, id, bank_account, salary, family_status, is_student, terms_of_employment, employment_start_date);
    }

    public EmployeeService(Employee employee) {
        this.employee = employee;
    }

    public boolean available_to_shift(String date, String branch, String type) {
        return employee.available_to_shift(date, branch, type);
    }

    public boolean remove_availability(String date, String branch, String type) {
        return employee.remove_availability(date, branch, type);
    }

    public Map<String, String> getAvailability(String branch) {
        return employee.getAvailability(branch);
    }

    public Map<String, String> getShifts(String branch) {
        return employee.getShifts(branch);
    }
}
