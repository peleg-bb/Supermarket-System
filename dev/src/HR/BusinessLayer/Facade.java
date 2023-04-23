package HR.BusinessLayer;

import HR.ServiceLayer.Response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Facade {

    private final EmployeeController employeeController;
    private final ShiftController shiftController;

    public Facade() {
        employeeController = new EmployeeController();
        shiftController = new ShiftController();
    }
    //V
    public String add_availability(Integer id, LocalDate date_object, ShiftType type, String store) {
        if (!employeeController.is_loggedIn(id)) {
            return "User not logged in";
        }
        return shiftController.add_availability(id, date_object, type, store);
    }
    //V
    public String remove_availability(Integer id, LocalDate date_object, ShiftType type, String store) {
        if (!employeeController.is_loggedIn(id)) {
            return "User not logged in";
        }
        return shiftController.remove_availability(id, date_object, type, store);
    }
    //V
    public String get_availability(Integer id) {
        if (!employeeController.is_loggedIn(id)) {
            return "User not logged in";
        }
        List<String> certified_stores = employeeController.get_certified_stores(id);
        return shiftController.get_availability(id, certified_stores);
    }
    //V
    public String get_shifts(Integer id) {
        if (!employeeController.is_loggedIn(id)) {
            return "User not logged in";
        }
        List<String> certified_stores = employeeController.get_certified_stores(id);
        return shiftController.get_shifts(id, certified_stores);
    }
    //V
    public String login(int id, String password) {
        return employeeController.login(id, password);
    }
    //V
    public String logout(int id_num) {
        return employeeController.logout(id_num);
    }
    //V
    public String add_employee(Integer id, int id_num, String name, int bank_account_num, int salary_num, String terms_of_employment, LocalDate date_object, String family_status, boolean student, String password) {
        return employeeController.add_employee(id, id_num, name, bank_account_num, salary_num, terms_of_employment, date_object, family_status, student, password);
    }
    //V
    public String remove_employee(Integer id, int id_num) {
        List<String> certified_stores = employeeController.get_certified_stores(id);
        if (shiftController.has_future_shifts(certified_stores, id_num)) {
            return "Employee is assigned to future shifts and can't be deleted.";
        }
        return employeeController.remove_employee(id, id_num);
    }
    //V
    public String certify_role(Integer id, int id_num, JobType role) {
        return employeeController.certify_role(id, id_num, role);
    }
    //V
    public String remove_role(Integer id, int id_num, JobType job) {
        List<String> certified_stores = employeeController.get_certified_stores(id);
        if (shiftController.has_future_shifts_role(certified_stores, job, id_num)) {
            return "Employee is assigned to future shifts in this store and can't be deleted.";
        }
        return employeeController.remove_role(id, id_num, job);
    }
    //V
    public String assign_to_store(Integer id, int id_num, String store) {
        if (!shiftController.store_exists(store)) {
            return "Store doesn't exists";
        }
        return employeeController.assign_to_store(id, id_num, store);
    }
    //V
    public String unassign_to_store(Integer id, int id_num, String store) {
        if (!shiftController.store_exists(store)) {
            return "Store doesn't exists";
        }
        if (shiftController.has_future_shifts(store, id_num)) {
            return "Employee is assigned to future shifts in this store and can't be deleted.";
        }
        return employeeController.unassign_to_store(id, id_num, store);
    }
    //V
    public String create_store(Integer id, String store) {
        if (shiftController.store_exists(store)) {
            return "Store already exists";
        }
        String res = employeeController.assign_to_store(id, id, store);
        if (!res.equals("")) {
            return res;
        }
        return shiftController.create_store(store);
    }
    //V
    public String remove_store(Integer id, String store) {
        String res = employeeController.unassign_all_from_store(id, store);
        if (!res.equals("")) {
            return res;
        }
        return shiftController.remove_store(store);
    }

    public String confirm_shift(Integer id, LocalDate date_object, ShiftType shift, String store) {
        if (!employeeController.is_HRManager(id)) {
            return "User is not an HR manager";
        }
        if (!employeeController.is_loggedIn(id)) {
            return "User not logged in currently";
        }
        return shiftController.confirm_shift(date_object, shift, store);

    }
    //V
    public String create_weekly_schedule(Integer id, LocalDate date_object, String store, LocalTime morn_start, LocalTime morn_end, LocalTime eve_start, LocalTime eve_end) {
        if (!employeeController.is_HRManager(id)) {
            return "User is not an HR manager";
        }
        if (!employeeController.is_loggedIn(id)) {
            return "User not logged in currently";
        }
        return shiftController.create_weekly_schedule(date_object, store, morn_start, morn_end, eve_start, eve_end);
    }
    //V
    public String assign_shift(Integer id, int id_num, LocalDate date_object, ShiftType shift_type, String store, JobType role) {
        if (!employeeController.is_HRManager(id)) {
            return "User is not an HR manager";
        }
        if (!employeeController.is_loggedIn(id)) {
            return "User not logged in currently";
        }
        if (!employeeController.is_certified_to_role(id_num, role)) {
            return "User is not certified to this role";
        }
        if (!employeeController.is_certified_to_store(id_num, store)) {
            return "User is not assigned to this store";
        }
        List<String> certified_stores = employeeController.get_certified_stores(id_num);
        String res = shiftController.shifts_limit(certified_stores, id_num, date_object);
        if(!res.equals("")) {
            return res;
        }
        employeeController.add_hours_to_employee(id_num, get_hours(date_object, shift_type, store));
        return shiftController.assign_shift(id_num, date_object, shift_type, store, role);
    }
    //V
    public double get_hours(LocalDate date_object, ShiftType shift_type, String store) {
        return shiftController.get_hours(date_object, shift_type, store);
    }
    //V
    public String unassign_shift(Integer id, int id_num, LocalDate date_object, ShiftType shift_type, String store, JobType job) {
        if (!employeeController.is_HRManager(id)) {
            return "User is not an HR manager";
        }
        if (!employeeController.is_loggedIn(id)) {
            return "User not logged in currently";
        }
        employeeController.remove_hours_from_employee(id_num, get_hours(date_object, shift_type, store));
        return shiftController.unassign_shift(id_num, date_object, shift_type, store, job);
    }
    //V
    public String limit_work(Integer id, int id_num, LocalDate date_object, ShiftType shift_type, String store) {
        if (!employeeController.is_HRManager(id)) {
            return "User is not an HR manager";
        }
        if (!employeeController.is_loggedIn(id)) {
            return "User not logged in currently";
        }
        if (!employeeController.is_certified_to_store(id_num, store)) {
            return "User is not assigned to this store";
        }
        return shiftController.limit_work(id_num, date_object, shift_type, store);
    }
    //V
    public String remove_worker_limit(Integer id, int id_num, LocalDate date_object, ShiftType shift_type, String store) {
        if (!employeeController.is_HRManager(id)) {
            return "User is not an HR manager";
        }
        if (!employeeController.is_loggedIn(id)) {
            return "User not logged in currently";
        }
        if (!employeeController.is_certified_to_store(id_num, store)) {
            return "User is not assigned to this store";
        }
        return shiftController.remove_worker_limit(id_num, date_object, shift_type, store);
    }
    //V
    public String show_shift_availability(Integer id, LocalDate date_object, ShiftType shift_type, String store) {
        if (!employeeController.is_HRManager(id)) {
            return "User is not an HR manager";
        }
        if (!employeeController.is_loggedIn(id)) {
            return "User not logged in currently";
        }
        if (!shiftController.store_exists(store)) {
            return "Store doesn't exists";
        }
        StringBuilder output = new StringBuilder();
        List<Integer> employees =  shiftController.show_shift_availability(date_object, shift_type, store);
        for (Integer employee: employees) {
            output.append(employeeController.get_name(employee)).append(", ").append(employee).append(" - ");
            for (JobType role: employeeController.get_certified_roles(employee)) {
                output.append(role.toString()).append(", ");
            }
            output = new StringBuilder(output.substring(0, output.length() - 2));
        }
        return output.toString();
    }
    //V
    public String change_name(Integer this_id, String old_name, String new_name) {
        return employeeController.change_name(this_id, old_name, new_name);
    }
    //V
    public String change_bank_account(Integer this_id, Integer old_bank_account, Integer new_bank_account) {
        return employeeController.change_bank_account(this_id, old_bank_account, new_bank_account);
    }
    //V
    public String change_family_status(Integer this_id, String old_family_status, String new_family_status) {
        return employeeController.change_family_status(this_id, old_family_status, new_family_status);
    }
    //V
    public String change_student(Integer this_id, boolean old_student_status, boolean new_student_status) {
        return employeeController.change_student(this_id, old_student_status, new_student_status);
    }
    //V
    public String change_employee_salary(Integer this_id, Integer employee_id, Integer old_salary, Integer new_salary) {
        return employeeController.change_employee_salary(this_id, employee_id, old_salary, new_salary);
    }
    //V
    public String change_employee_terms(Integer this_id, Integer employee_id, String new_terms) {
        return employeeController.change_employee_terms(this_id, employee_id, new_terms);
    }
    //V
    public String confirm_monthly_salary(int HR_id, int id_num, int bonus_num) {
        if (!employeeController.is_HRManager(HR_id)) {
            return "User is not an HR manager";
        }
        if (!employeeController.is_loggedIn(HR_id)) {
            return "User not logged in currently";
        }
        return employeeController.confirm_monthly_salary(HR_id, id_num, bonus_num);
    }

    public boolean is_hr(int id) {
        return employeeController.is_HRManager(id);
    }

    public String show_personal_info(Integer id) {
        return employeeController.show_personal_info(id);
    }

    public String show_role_certifications(Integer id) {
        return employeeController.show_role_certifications(id);
    }

    public String show_assigned_stores(Integer id) {
        return employeeController.show_assigned_stores(id);
    }

    public String show_current_salary(Integer id) {
        return employeeController.show_current_salary(id);
    }

    public void add_hr(Integer id, String name, Integer bank_account, double salary, String terms_of_employment, LocalDate employment_date, String family_status, boolean is_student, String password) {
        employeeController.add_hr(id, name, bank_account, salary, terms_of_employment, employment_date, family_status, is_student, password);
    }

    public String show_employees(int hr_id) {
        if (!employeeController.is_HRManager(hr_id)) {
            return "User is not an HR manager";
        }
        if (!employeeController.is_loggedIn(hr_id)) {
            return "User not logged in currently";
        }
        return employeeController.show_employees(hr_id);
    }

    public String show_employee_info(int id, int employee_id) {
        if (!employeeController.is_HRManager(id)) {
            return "User is not an HR manager";
        }
        if (!employeeController.is_loggedIn(id)) {
            return "User not logged in currently";
        }
        return employeeController.show_employee_info(employee_id);
    }

    public String load_data() {
        Response res1 = new Response(employeeController.load_data());
        if (res1.errorOccurred()) {
            return res1.getErrorMessage();
        }
        List<String> stores = employeeController.get_certified_stores(employeeController.get_hr_id());
        Response res2 = new Response(shiftController.load_data(stores));
        if (res2.errorOccurred()) {
            return res2.getErrorMessage();
        }
        return "";
    }

    public String cancel_product(int id, int product_id_num, LocalDate date_object, ShiftType type, String store) {
        if (!employeeController.is_ShiftManager(id)) {
            return "User is not a shift manager and can't cancel a product";
        }
        if (!employeeController.is_loggedIn(id)) {
            return "User not logged in currently";
        }
        return shiftController.cancel_product(id, product_id_num, date_object, type, store);
    }

    public String show_shift_assigned(int id, LocalDate date_object, ShiftType shift_type, String store) {
        if (!employeeController.is_HRManager(id)) {
            return "User is not an HR manager";
        }
        if (!employeeController.is_loggedIn(id)) {
            return "User not logged in currently";
        }
        if (!shiftController.store_exists(store)) {
            return "Store doesn't exists";
        }
        return shiftController.show_shift_assigned(date_object, shift_type, store);
    }

    //Tests functions:
    public boolean employee_exists(int id) {
        return employeeController.employee_exists(id);
    }

    public boolean employee_logged_in(int id) {
        return employeeController.employee_logged_in(id);
    }

    public boolean store_exists(String store) {
        return shiftController.store_exists(store);
    }

    public double get_employee_salary(int id) {
        return employeeController.get_employee_salary(id);
    }

    public String get_employee_name(int id) {
        return employeeController.get_employee_name(id);
    }

    public int get_employee_bank_account(int id) {
        return employeeController.get_employee_bank_account(id);
    }

    public String get_employee_family_status(int id) {
        return employeeController.get_employee_family_status(id);
    }

    public boolean get_employee_student_status(int id) {
        return employeeController.get_employee_student_status(id);
    }

    public String get_employee_terms(int id) {
        return employeeController.get_employee_terms(id);
    }

    public boolean assigned_to_store(int id, String store) {
        return employeeController.assigned_to_store(id, store);
    }

    public boolean certified_to_role(int id, JobType role) {
        return employeeController.is_certified_to_role(id, role);
    }

    public boolean is_limited(int id, LocalDate date_object, ShiftType shift_type, String store) {
        return shiftController.is_limited(id, date_object, shift_type, store);
    }

    public boolean future_schedule_exists(LocalDate date_object, String store) {
        return shiftController.future_schedule_exists(date_object, store);
    }

    public boolean past_schedule_exists(LocalDate date_object, String store) {
        return shiftController.past_schedule_exists(date_object, store);
    }

    public double get_monthly_salary(int manager_id, int employee_id) {
        return employeeController.get_monthly_salary(manager_id, employee_id);
    }
}
