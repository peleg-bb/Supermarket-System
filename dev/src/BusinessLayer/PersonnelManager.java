package BusinessLayer;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class PersonnelManager {
    private String name;
    private Integer id;
    private String terms_of_employment;
    private LocalDate employment_start_date;
    private Schedules schedules;
    private Map<String, List<Worker>> roles_employees;
    private Map<Integer, Worker> employees;

    public PersonnelManager(String name, Integer id, String terms_of_employment, LocalDate employment_start_date, Schedules schedules, Map<String, List<Worker>> roles_employees, Map<Integer, Worker> employees) {
        this.name = name;
        this.id = id;
        this.terms_of_employment = terms_of_employment;
        this.employment_start_date = employment_start_date;
        this.schedules = schedules;
        this.roles_employees = roles_employees;
        this.employees = employees;
    }

    public boolean assign_to_shift(Integer ID, LocalDate date, Shift.shift_type type, String branch, String role) {
        if (check_employee_availability(ID, date, type, branch, role)) {
            schedules.getBranchScheduleMap().get(branch).assign_shift(ID, date, type, role);
            return true;
        }
        return false;
    }

    public boolean remove_shift(Integer ID, LocalDate date, Shift.shift_type type, String branch, String role) {
        if (!schedules.getBranchScheduleMap().get(branch).is_assigned(ID, date, type, role)) {
            return false;
        }
        schedules.getBranchScheduleMap().get(branch).remove_shift(ID, date, type, role);
        return true;
    }

    public boolean add_employee(String name, Integer id, Integer bank_account, Integer salary, String family_status, boolean is_student, String terms_of_employment, LocalDate employment_start_date, Schedules schedules) {
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

    public boolean confirm_shift(LocalDate date, Shift.shift_type type, String branch) {
        return schedules.getBranchScheduleMap().get(branch).confirm_shift(date, type);
    }

    private boolean check_employee_availability(Integer ID, LocalDate date, Shift.shift_type type, String branch, String role) {
        if (schedules.getBranchScheduleMap().get(branch).check_employee_availability(date, type, ID) && check_employee_role(ID, role)) {
            return true;
        }
        return false;
    }

    private boolean check_employee_role(Integer ID, String role) {
        return roles_employees.get(role).contains(employees.get(ID));
    }

    public void create_schedule(String branch, LocalDate week_first_day) {
        BranchSchedule schedule = new BranchSchedule(branch, week_first_day);
        schedules.add_schedule(branch, schedule);
    }
}
