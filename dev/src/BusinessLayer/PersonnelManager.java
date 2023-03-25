package BusinessLayer;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class PersonnelManager {
    private String name;
    private Integer id;
    private String terms_of_employment;
    private LocalDate employment_start_date;
    private Map<String, Schedule> schedules;
    private Map<String, List<Worker>> roles_employees;
    private Map<Integer, Worker> employees;

    public PersonnelManager(String name, Integer id, String terms_of_employment, LocalDate employment_start_date, Map<String, Schedule> schedules, Map<String, List<Worker>> roles_employees, Map<Integer, Worker> employees) {
        this.name = name;
        this.id = id;
        this.terms_of_employment = terms_of_employment;
        this.employment_start_date = employment_start_date;
        this.schedules = schedules;
        this.roles_employees = roles_employees;
        this.employees = employees;
    }

    public boolean assign_to_shift(Integer ID, Shift shift, String role) {
        String branch = shift.getBranch();
        if (check_employee_availability(ID, shift, role)) {
            schedules.get(branch).assign_shift(ID, shift, role);
            return true;
        }
        return false;
    }

    public boolean remove_shift(Integer ID, Shift shift, String role) {
        String branch = shift.getBranch();
        if (!schedules.get(branch).is_assigned(ID, shift, role)) {
            return false;
        }
        schedules.get(branch).remove_shift(ID, shift, role);
        return true;
    }

    public boolean add_employee(String name, Integer id, Integer bank_account, Integer salary, String family_status, boolean is_student, String terms_of_employment, LocalDate employment_start_date, Map<String, Schedule> schedules) {
        Worker employee = new Worker(name, id, bank_account, salary, family_status, is_student, terms_of_employment, employment_start_date, schedules);
        employees.put(id, employee);
        return true;
    }

    public boolean remove_employee(Integer id) {
        employees.remove(id);
        return true;
    }

    public boolean add_employee_role(Integer id, String role) {
        return employees.get(id).add_role(role);
    }

    public boolean remove_employee_role(Integer id, String role) {
        return employees.get(id).remove_role(role);
    }

    public boolean confirm_shift(Shift shift) {
        if (shift.getShift_manager() != null) {
            return true;
        }
        return false;
    }

    private boolean check_employee_availability(Integer ID, Shift shift, String role) {
        String branch = shift.getBranch();
        if (schedules.get(branch).check_employee_availability(shift, ID) && check_employee_role(ID, role)) {
            return true;
        }
        return false;
    }

    private boolean check_employee_role(Integer ID, String role) {
        return roles_employees.get(role).contains(employees.get(ID));
    }
}
