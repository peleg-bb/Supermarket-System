package HR.BusinessLayer;

import java.util.*;

public class EmployeeController {
    private Map<Integer, String> login_info; //<id, password>
    private List<Integer> logged_in;
    private Map<Integer, Employee> employees;

    public EmployeeController() {
        login_info = new HashMap<>();
        logged_in = new LinkedList<>();
        employees = new HashMap<>();
    }

    public String login(int id, String password) {
        if (!login_info.containsKey(id)) {
            return "User doesn't exists";
        }
        if (!login_info.get(id).equals(password)) {
            return "Password is wrong";
        }
        if (is_loggedIn(id)) {
            return "User already logged in";
        }
        logged_in.add(id);
        return "";
    }

    public String logout(int id) {
        if (!login_info.containsKey(id)) {
            return "User doesn't exists";
        }
        if (!is_loggedIn(id)) {
            return "User already logged out";
        }
        logged_in.remove(id);
        return "";
    }

    public String get_schedule(int id) {
        return "";
    }

    public boolean is_loggedIn(int id) {
        return logged_in.contains(id);
    }

    public List<String> get_certified_stores(Integer id) {
        return employees.get(id).get_stores();
    }

    public String add_employee(Integer id, int id_num, String name, int bank_account_num, int salary_num, String terms_of_employment, Date date_object, String family_status, boolean student, String password) {
        if (!is_HRManager(id)) {
            return "User is not an HR manager";
        }
        if (employees.containsKey(id_num)) {
            return "Employee already exists";
        }
        if (!is_loggedIn(id)) {
            return "User not logged in currently";
        }
        employees.put(id_num, new Employee(id_num, name, bank_account_num, salary_num, terms_of_employment, date_object, family_status, student));
        login_info.put(id_num, password);
        return "";
    }

    public boolean is_HRManager(int id) {
        return employees.get(id).is_HR();
    }

    public String remove_employee(Integer id, int id_num) {
        if (!is_HRManager(id)) {
            return "User is not an HR manager";
        }
        if (!employees.containsKey(id_num)) {
            return "Employee doesn't exists";
        }
        if (!is_loggedIn(id)) {
            return "User not logged in currently";
        }
        employees.remove(id_num);
        login_info.remove(id_num);
        return "";
    }

    public String certify_role(Integer id, int id_num, JobType role) {
        if (!is_HRManager(id)) {
            return "User is not an HR manager";
        }
        if (!employees.containsKey(id_num)) {
            return "Employee doesn't exists";
        }
        if (!is_loggedIn(id)) {
            return "User not logged in currently";
        }
        return employees.get(id_num).certify_role(role);
    }

    public String remove_role(Integer id, int id_num, JobType job) {
        if (!is_HRManager(id)) {
            return "User is not an HR manager";
        }
        if (!employees.containsKey(id_num)) {
            return "Employee doesn't exists";
        }
        if (!is_loggedIn(id)) {
            return "User not logged in currently";
        }
        return employees.get(id_num).remove_role(job);
    }

    public String assign_to_store(Integer id, int id_num, String store) {
        if (!is_HRManager(id)) {
            return "User is not an HR manager";
        }
        if (!employees.containsKey(id_num)) {
            return "Employee doesn't exists";
        }
        if (!is_loggedIn(id)) {
            return "User not logged in currently";
        }
        return employees.get(id_num).assign_to_store(store);
    }

    public String unassign_to_store(Integer id, int id_num, String store) {
        if (!is_HRManager(id)) {
            return "User is not an HR manager";
        }
        if (!employees.containsKey(id_num)) {
            return "Employee doesn't exists";
        }
        if (!is_loggedIn(id)) {
            return "User not logged in currently";
        }
        return employees.get(id_num).unassign_to_store(store);
    }

    public boolean is_certified_to_role(int id_num, JobType role) {
        return employees.get(id_num).is_certified_to_role(role);
    }

    public boolean is_certified_to_store(int id_num, String store) {
        return employees.get(id_num).is_certified_to_store(store);
    }

    public List<JobType> get_certified_roles(Integer id) {
        return employees.get(id).get_roles();
    }

    public String get_name(Integer id) {
        return employees.get(id).get_name();
    }
}
