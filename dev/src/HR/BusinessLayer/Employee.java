package HR.BusinessLayer;

import java.util.Date;
import java.util.List;

public class Employee {
    private final Integer id;
    private String name;
    private Integer bank_account;
    private float salary;
    private String terms_of_employment;
    private final Date employment_date;
    private String family_status;
    private boolean is_student;
    private List<JobType> roles;
    private List<String> certified_stores;

    public Employee(Integer id, String name, Integer bank_account, float salary, String terms_of_employment, Date employment_date, String family_status, boolean is_student) {
        this.id = id;
        this.name = name;
        this.bank_account = bank_account;
        this.salary = salary;
        this.terms_of_employment = terms_of_employment;
        this.employment_date = employment_date;
        this.family_status = family_status;
        this.is_student = is_student;
    }

    public List<String> get_stores() {
        return certified_stores;
    }

    public boolean is_HR() {
        return roles.contains(JobType.HRMANAGER);
    }

    public String certify_role(JobType role) {
        if (roles.contains(role)) {
            return "Employee already certified to this role";
        }
        roles.add(role);
        return "";
    }

    public String remove_role(JobType job) {
        if (roles.contains(job)) {
            roles.remove(job);
            return "";
        }
        return "Employee isn't certified to this role";
    }

    public String assign_to_store(String store) {
        if (certified_stores.contains(store)) {
            return "Already assigned to this store";
        }
        certified_stores.add(store);
        return "";
    }

    public String unassign_to_store(String store) {
        if (certified_stores.contains(store)) {
            certified_stores.remove(store);
            return "";
        }
        return "Already not assigned to this store";
    }

    public boolean is_certified_to_role(JobType role) {
        return roles.contains(role);
    }

    public boolean is_certified_to_store(String store) {
        return certified_stores.contains(store);
    }

    public String get_name() {
        return name;
    }

    public List<JobType> get_roles() {
        return roles;
    }
}
