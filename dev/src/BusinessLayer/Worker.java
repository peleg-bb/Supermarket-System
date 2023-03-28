package BusinessLayer;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Worker {
    private String name;
    private Integer id;
    private Integer bank_account;
    private Integer salary;
    private String family_status;
    private boolean is_student;
    private String terms_of_employment;
    private LocalDate employment_start_date;
    private Schedules schedules;
    private List<String> roles;

    public Worker(String name, Integer id, Integer bank_account, Integer salary, String family_status, boolean is_student, String terms_of_employment, LocalDate employment_start_date, Schedules schedules) {
        this.name = name;
        this.id = id;
        this.bank_account = bank_account;
        this.salary = salary;
        this.family_status = family_status;
        this.is_student = is_student;
        this.terms_of_employment = terms_of_employment;
        this.employment_start_date = employment_start_date;
        this.schedules = schedules;
        roles = new LinkedList<>();
    }

    public boolean available_to_shift(LocalDate date, String branch, Shift.shift_type type) {
        if (check_manager_constraint(date, branch, type)) {
            schedules.getBranchScheduleMap().get(branch).add_availability(date, type, this.id);
            return true;
        }
        else {
            return false;
        }
    }

    private boolean check_manager_constraint(LocalDate date, String branch, Shift.shift_type type) {
        return schedules.getBranchScheduleMap().get(branch).check_manager_constraint(date, type, this.id);
    }

    public boolean add_role(String role) {
        if (roles.contains(role)) {
            return false;
        }
        roles.add(role);
        return true;
    }

    public boolean remove_role(String role) {
        if (!roles.contains(role)) {
            return false;
        }
        roles.remove(role);
        return true;
    }

    public List<String> getRoles() {
        return roles;
    }
}
