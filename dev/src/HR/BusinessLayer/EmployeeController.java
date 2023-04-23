package HR.BusinessLayer;

import HR.DataAccessLayer.EmployeeDAO;

import java.util.*;

public class EmployeeController {
    private final Map<Integer, String> login_info; //<id, password>
    private final List<Integer> logged_in;
    private final Map<Integer, Employee> employees;
    private final EmployeeDAO employeeDAO;

    public EmployeeController() {
        login_info = new HashMap<>();
        logged_in = new LinkedList<>();
        employees = new HashMap<>();
        employeeDAO = new EmployeeDAO();
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
        logged_in.remove(Integer.valueOf(id));
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
        String res = employeeDAO.add_employee(id_num, name, bank_account_num, salary_num, terms_of_employment, date_object, family_status, student, password);
        if (res.equals("")) {
            employees.put(id_num, new Employee(id_num, name, bank_account_num, salary_num, terms_of_employment, date_object, family_status, student, this.employeeDAO));
            login_info.put(id_num, password);
            return res;
        }
        return res;
    }

    public boolean is_HRManager(int id) {
        if (!employees.containsKey(id)) {
            return false;
        }
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
        String res = employeeDAO.remove_employee(id_num);
        if (res.equals("")) {
            employees.remove(id_num);
            login_info.remove(id_num);
            return res;
        }
        return res;
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

    public String change_name(Integer this_id, String old_name, String new_name) {
        if (!is_loggedIn(this_id)) {
            return "User not logged in currently";
        }
        return employees.get(this_id).change_name(old_name, new_name);
    }

    public String change_bank_account(Integer this_id, Integer old_bank_account, Integer new_bank_account) {
        if (!is_loggedIn(this_id)) {
            return "User not logged in currently";
        }
        return employees.get(this_id).change_bank_account(old_bank_account, new_bank_account);
    }

    public String change_family_status(Integer this_id, String old_family_status, String new_family_status) {
        if (!is_loggedIn(this_id)) {
            return "User not logged in currently";
        }
        return employees.get(this_id).change_family_status(old_family_status, new_family_status);
    }

    public String change_student(Integer this_id, boolean old_student_status, boolean new_student_status) {
        if (!is_loggedIn(this_id)) {
            return "User not logged in currently";
        }
        return employees.get(this_id).change_student(old_student_status, new_student_status);
    }

    public String change_employee_salary(Integer this_id, Integer employee_id, Integer old_salary, Integer new_salary) {
        if (!is_HRManager(this_id)) {
            return "User is not an HR manager";
        }
        if (!employees.containsKey(employee_id)) {
            return "Employee doesn't exists";
        }
        if (!is_loggedIn(this_id)) {
            return "User not logged in currently";
        }
        return employees.get(employee_id).change_employee_salary(old_salary, new_salary);
    }

    public String change_employee_terms(Integer this_id, Integer employee_id, String new_terms) {
        if (!is_HRManager(this_id)) {
            return "User is not an HR manager";
        }
        if (!employees.containsKey(employee_id)) {
            return "Employee doesn't exists";
        }
        if (!is_loggedIn(this_id)) {
            return "User not logged in currently";
        }
        return employees.get(employee_id).change_employee_terms(new_terms);
    }

    public String unassign_all_from_store(int id_num, String store) {
        if (!is_HRManager(id_num)) {
            return "User is not an HR manager";
        }
        if (!is_loggedIn(id_num)) {
            return "User not logged in currently";
        }
        for (Integer id: employees.keySet()) {
            if (employees.get(id).is_certified_to_store(store)) {
                employees.get(id).unassign_to_store(store);
            }
        }
        return "";
    }

    public void add_hours_to_employee(int id_num, double hours) {
        employees.get(id_num).add_hours(hours);
    }

    public void remove_hours_from_employee(int id_num, double hours) {
        employees.get(id_num).remove_hours(hours);
    }

    public String confirm_monthly_salary(int this_id, int id_num, int bonus_num) {
        if (!is_HRManager(this_id)) {
            return "User is not an HR manager";
        }
        if (!employees.containsKey(id_num)) {
            return "Employee doesn't exists";
        }
        if (!is_loggedIn(this_id)) {
            return "User not logged in currently";
        }
        return employees.get(id_num).confirm_monthly_salary(bonus_num);
    }

    public String show_personal_info(Integer id) {
        if (!employees.containsKey(id)) {
            return "Employee doesn't exists";
        }
        if (!is_loggedIn(id)) {
            return "User not logged in currently";
        }
        return employees.get(id).show_personal_info();
    }

    public String show_role_certifications(Integer id) {
        if (!employees.containsKey(id)) {
            return "Employee doesn't exists";
        }
        if (!is_loggedIn(id)) {
            return "User not logged in currently";
        }
        return employees.get(id).show_role_certifications();
    }

    public String show_assigned_stores(Integer id) {
        if (!employees.containsKey(id)) {
            return "Employee doesn't exists";
        }
        if (!is_loggedIn(id)) {
            return "User not logged in currently";
        }
        return employees.get(id).show_assigned_stores();
    }

    public String show_current_salary(Integer id) {
        if (!employees.containsKey(id)) {
            return "Employee doesn't exists";
        }
        if (!is_loggedIn(id)) {
            return "User not logged in currently";
        }
        return employees.get(id).show_current_salary();
    }

    public void add_hr(Integer id, String name, Integer bank_account, double salary, String terms_of_employment, Date employment_date, String family_status, boolean is_student, String password) {
        employeeDAO.add_employee(id, name, bank_account, salary, terms_of_employment, employment_date, family_status, is_student, password);
        Employee employee = new Employee(id, name, bank_account, salary, terms_of_employment, employment_date, family_status, is_student, this.employeeDAO);
        employee.certify_role(JobType.HRMANAGER);
        employees.put(id, employee);
        login_info.put(id, password);
    }

    public String show_employees(int hr_id) {
        StringBuilder output = new StringBuilder();
        for (Integer id: employees.keySet()) {
            if (id != hr_id) {
                output.append(id).append(" - ").append(employees.get(id).get_name()).append("\n");
            }
        }
        return output.toString();
    }

    public String show_employee_info(int employee_id) {
        if (!employees.containsKey(employee_id)) {
            return "Employee doesn't exist";
        }
        return employees.get(employee_id).show_personal_info();
    }

    public String load_data() {
        try {
            Map<Employee, String> employees = employeeDAO.load_Data();
            for (Employee employee: employees.keySet()) {
                this.employees.put(employee.get_id(), employee);
                login_info.put(employee.get_id(), employees.get(employee));
            }
            return "";
        }
        catch (Exception exception) {
            return "Restoring the employees went wrong!";
        }
    }

    public Integer get_hr_id() {
        for (Integer id: employees.keySet()) {
            if (employees.get(id).is_HR()) {
                return id;
            }
        }
        return null;
    }

    public boolean is_ShiftManager(int id) {
        return employees.get(id).is_shift_manager();
    }


    //Tests functions:
    public boolean employee_exists(int id) {
        return employees.containsKey(id);
    }

    public boolean employee_logged_in(int id) {
        return logged_in.contains(id);
    }

    public double get_employee_salary(int id) {
        return employees.get(id).get_salary();
    }

    public String get_employee_name(int id) {
        return employees.get(id).get_name();
    }

    public int get_employee_bank_account(int id) {
        return employees.get(id).get_bank();
    }

    public String get_employee_family_status(int id) {
        return employees.get(id).get_family_status();
    }

    public boolean get_employee_student_status(int id) {
        return employees.get(id).get_student();
    }

    public String get_employee_terms(int id) {
        return employees.get(id).get_terms();
    }

    public boolean assigned_to_store(int id, String store) {
        return employees.get(id).is_certified_to_store(store);
    }
}
