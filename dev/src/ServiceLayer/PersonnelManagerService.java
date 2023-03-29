package ServiceLayer;

import BusinessLayer.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class PersonnelManagerService {
    PersonnelManager personnelManager;

    public PersonnelManagerService(String name, Integer id, Integer bank_account, Integer salary, String family_status, boolean is_student, String terms_of_employment, LocalDate employment_start_date, Map<String, List<Integer>> roles_employees, Map<Integer, Worker> employees, Map<String, Branch> branches) {
        personnelManager = new PersonnelManager(name, id, bank_account, salary, family_status, is_student, terms_of_employment, employment_start_date, roles_employees, employees, branches);
    }

    public boolean assign_to_shift(Integer ID, LocalDate date, Shift.shift_type type, String branch, String role) {
        return personnelManager.assign_to_shift(ID, date, type, branch, role);
    }

    public boolean remove_shift(Integer ID, LocalDate date, Shift.shift_type type, String branch, String role) {
        return personnelManager.remove_shift(ID, date, type, branch, role);
    }

    public boolean add_employee(String name, Integer id, Integer bank_account, Integer salary, String family_status, boolean is_student, String terms_of_employment, LocalDate employment_start_date) {
        return personnelManager.add_employee(name, id, bank_account, salary, family_status, is_student, terms_of_employment, employment_start_date);
    }

    public boolean remove_employee(Integer id) {
        return personnelManager.remove_employee(id);
    }

    public boolean add_employee_role(Integer id, String role) {
        return personnelManager.add_employee_role(id, role);
    }

    public boolean remove_employee_role(Integer id, String role) {
        return personnelManager.remove_employee_role(id, role);
    }

    public boolean confirm_shift(LocalDate date, Shift.shift_type type, String branch) {
        return personnelManager.confirm_shift(date, type, branch);
    }

    public void create_schedule(String branch, LocalDate week_first_day) {
        personnelManager.create_schedule(branch, week_first_day);
    }

    public boolean create_branch(String branch_name, String location, Integer morning_shift_hours, Integer evening_shift_hours) {
        return personnelManager.create_branch(branch_name, location, morning_shift_hours, evening_shift_hours);
    }

    public boolean qualify_employee_to_branch(Integer id, String branch) {
        return personnelManager.qualify_employee_to_branch(id, branch);
    }

    public boolean assign_shift_manager(LocalDate date, Shift.shift_type type, String branch, Integer id) {
        return personnelManager.assign_shift_manager(date, type, branch, id);
    }
}
