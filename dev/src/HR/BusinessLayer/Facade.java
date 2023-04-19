package HR.BusinessLayer;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class Facade {

    private EmployeeController employeeController;
    private ShiftController shiftController;
    public String add_availability(Integer id, Date date_object, ShiftType type, String store) {
        if (!employeeController.is_loggedIn(id)) {
            return "User not logged in";
        }
        return shiftController.add_availability(id, date_object, type, store);
    }

    public String remove_availability(Integer id, Date date_object, ShiftType type, String store) {
        if (!employeeController.is_loggedIn(id)) {
            return "User not logged in";
        }
        return shiftController.remove_availability(id, date_object, type, store);
    }

    public String get_availability(Integer id) {
        if (!employeeController.is_loggedIn(id)) {
            return "User not logged in";
        }
        List<String> certified_stores = employeeController.get_certified_stores(id);
        return shiftController.get_availability(id, certified_stores);
    }

    public String get_shifts(Integer id) {
        if (!employeeController.is_loggedIn(id)) {
            return "User not logged in";
        }
        List<String> certified_stores = employeeController.get_certified_stores(id);
        return shiftController.get_shifts(id, certified_stores);
    }

    public String login(int id, String password) {
        return employeeController.login(id, password);
    }

    public String logout(int id_num) {
        return employeeController.logout(id_num);
    }

    public String add_employee(Integer id, int id_num, String name, int bank_account_num, int salary_num, String terms_of_employment, Date date_object, String family_status, boolean student, String password) {
        return employeeController.add_employee(id, id_num, name, bank_account_num, salary_num, terms_of_employment, date_object, family_status, student, password);
    }

    public String remove_employee(Integer id, int id_num) {
        return employeeController.remove_employee(id, id_num);
    }

    public String certify_role(Integer id, int id_num, JobType role) {
        return employeeController.certify_role(id, id_num, role);
    }

    public String remove_role(Integer id, int id_num, JobType job) {
        return employeeController.remove_role(id, id_num, job);
    }

    public String assign_to_store(Integer id, int id_num, String store) {
        if (!shiftController.store_exists(store)) {
            return "Store doesn't exists";
        }
        return employeeController.assign_to_store(id, id_num, store);
    }

    public String unassign_to_store(Integer id, int id_num, String store) {
        if (!shiftController.store_exists(store)) {
            return "Store doesn't exists";
        }
        return employeeController.unassign_to_store(id, id_num, store);
    }

    public String create_store(Integer id, String store) {
        if (!employeeController.is_HRManager(id)) {
            return "User is not an HR manager";
        }
        if (!employeeController.is_loggedIn(id)) {
            return "User not logged in currently";
        }
        return shiftController.create_store(id, store);
    }

    public String remove_store(Integer id, String store) {
        if (!employeeController.is_HRManager(id)) {
            return "User is not an HR manager";
        }
        if (!employeeController.is_loggedIn(id)) {
            return "User not logged in currently";
        }
        return shiftController.remove_store(id, store);
    }

    public String confirm_shift(Integer id, Date date_object, ShiftType shift, String store) {
        if (!employeeController.is_HRManager(id)) {
            return "User is not an HR manager";
        }
        if (!employeeController.is_loggedIn(id)) {
            return "User not logged in currently";
        }
        return shiftController.confirm_shift(date_object, shift, store);

    }

    public String create_weekly_schedule(Integer id, Date date_object, String store, Time morn_start, Time morn_end, Time eve_start, Time eve_end) {
        if (!employeeController.is_HRManager(id)) {
            return "User is not an HR manager";
        }
        if (!employeeController.is_loggedIn(id)) {
            return "User not logged in currently";
        }
        return shiftController.create_weekly_schedule(date_object, store, morn_start, morn_end, eve_start, eve_end);
    }

    public String assign_shift(Integer id, int id_num, Date date_object, ShiftType shift_type, String store, JobType role) {
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
        if(!shiftController.shifts_limit(certified_stores, id_num, date_object, shift_type).equals("")) {
            return shiftController.shifts_limit(certified_stores, id_num, date_object, shift_type);
        }
        return shiftController.assign_shift(id_num, date_object, shift_type, store, role);
    }

    public String unassign_shift(Integer id, int id_num, Date date_object, ShiftType shift_type, String store, JobType job) {
        if (!employeeController.is_HRManager(id)) {
            return "User is not an HR manager";
        }
        if (!employeeController.is_loggedIn(id)) {
            return "User not logged in currently";
        }
        return shiftController.unassign_shift(id_num, date_object, shift_type, store, job);
    }

    public String limit_work(Integer id, int id_num, Date date_object, ShiftType shift_type, String store) {
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

    public String remove_worker_limit(Integer id, int id_num, Date date_object, ShiftType shift_type, String store) {
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

    public String show_shift_availability(Integer id, Date date_object, ShiftType shift_type, String store) {
        if (!employeeController.is_HRManager(id)) {
            return "User is not an HR manager";
        }
        if (!employeeController.is_loggedIn(id)) {
            return "User not logged in currently";
        }
        if (!shiftController.store_exists(store)) {
            return "Store doesn't exists";
        }
        String output = "";
        List<Integer> employees =  shiftController.show_shift_availability(date_object, shift_type, store);
        for (Integer employee: employees) {
            output = output + employeeController.get_name(id) + ", " + id + " - ";
            for (JobType role: employeeController.get_certified_roles(id)) {
                output = output + role.toString() + ", ";
            }
            output = output.substring(0, output.length() - 3);
        }
        return output;
    }
}
