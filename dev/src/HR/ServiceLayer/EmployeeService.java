package HR.ServiceLayer;

import HR.BusinessLayer.Facade;
import HR.BusinessLayer.JobType;
import HR.BusinessLayer.ShiftType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class EmployeeService {

    private final Facade facade;
    private int id = -1;

    public EmployeeService() {
        facade = new Facade();
    }

    public Response add_availability(String date, String shift_type, String store) {
        if (!shift_type.equalsIgnoreCase("morning") && !shift_type.equalsIgnoreCase("evening")) {
            return new Response("Invalid shift type");
        }
        LocalDate date_object;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            date_object = LocalDate.parse(date, formatter);
        }
        catch(Exception exception) {
            return new Response("Invalid date");
        }
        return new Response(facade.add_availability(id, date_object, ShiftType.valueOf(shift_type.toUpperCase()), store));
    }

    public Response remove_availability(String date, String shift_type, String store) {
        if (!shift_type.equalsIgnoreCase("morning") && !shift_type.equalsIgnoreCase("evening")) {
            return new Response("Invalid shift type");
        }
        LocalDate date_object;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            date_object = LocalDate.parse(date, formatter);
        }
        catch(Exception exception) {
            return new Response("Invalid date");
        }
        return new Response(facade.remove_availability(id, date_object, ShiftType.valueOf(shift_type.toUpperCase()), store));
    }

    public Response get_availability() {
        return new Response(facade.get_availability(id));
    }

    public Response get_shifts() {
        return new Response(facade.get_shifts(id));
    }

    public Response login(String id, String password) {
        try {
            int id_num = Integer.parseInt(id);
            Response response = new Response(facade.login(id_num, password));
            if (response.errorOccurred()) {
                return response;
            }
            this.id = id_num;
            return response;
        }
        catch (Exception exception) {
            return new Response("Invalid id");
        }
    }

    public Response logout() {
        try {
            Response response = new Response(facade.logout(this.id));
            if (response.errorOccurred()) {
                return response;
            }
            this.id = -1;
            return response;
        }
        catch (Exception exception) {
            return new Response("Invalid id");
        }
    }

    public Response add_employee(String id, String name, String bank_account, String salary, String terms_of_employment, String employment_date, String family_status, String is_student, String password) {
        int id_num, bank_account_num, salary_num;
        LocalDate date_object;
        boolean student;
        try {id_num = Integer.parseInt(id);} catch (Exception exception) {return new Response("Invalid id");}
        try {bank_account_num = Integer.parseInt(bank_account);} catch (Exception exception) {return new Response("Invalid bank account");}
        try {salary_num = Integer.parseInt(salary);} catch (Exception exception) {return new Response("Invalid salary");}
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            date_object = LocalDate.parse(employment_date, formatter);
        }
        catch(Exception exception) {
            return new Response("Invalid date");
        }
        student = is_student.equalsIgnoreCase("yes");
        if (!name.matches("^[a-zA-Z ]+$")) {
            return new Response("Invalid name");
        }
        return new Response(facade.add_employee(this.id, id_num, name, bank_account_num, salary_num, terms_of_employment, date_object, family_status, student, password));
    }

    public Response remove_employee(String id) {
        int id_num;
        try {id_num = Integer.parseInt(id);} catch (Exception exception) {return new Response("Invalid id");}
        return new Response(facade.remove_employee(this.id, id_num));
    }

    public Response certify_role(String id, String role) {
        int id_num;
        JobType job;
        try {id_num = Integer.parseInt(id);} catch (Exception exception) {return new Response("Invalid id");}
        try {job = JobType.valueOf(role.toUpperCase()); } catch (Exception exception) {return new Response("Invalid role");}
        return new Response(facade.certify_role(this.id, id_num, job));
    }

    public Response remove_role(String id, String role) {
        int id_num;
        JobType job;
        try {id_num = Integer.parseInt(id);} catch (Exception exception) {return new Response("Invalid id");}
        try {job = JobType.valueOf(role.toUpperCase()); } catch (Exception exception) {return new Response("Invalid role");}
        return new Response(facade.remove_role(this.id, id_num, job));
    }

    public Response assign_to_store(String id, String store) {
        int id_num;
        try {id_num = Integer.parseInt(id);} catch (Exception exception) {return new Response("Invalid id");}
        return new Response(facade.assign_to_store(this.id, id_num, store));
    }

    public Response unassign_to_store(String id, String store) {
        int id_num;
        try {id_num = Integer.parseInt(id);} catch (Exception exception) {return new Response("Invalid id");}
        return new Response(facade.unassign_to_store(this.id, id_num, store));
    }

    public Response create_store(String store) {
        return new Response(facade.create_store(this.id, store));
    }

    public Response remove_store(String store) {
        return new Response(facade.remove_store(this.id, store));
    }

    public Response confirm_shift(String date, String shift_type, String store) {
        if (!shift_type.equalsIgnoreCase("morning") && !shift_type.equalsIgnoreCase("evening")) {
            return new Response("Invalid shift type");
        }
        LocalDate date_object;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            date_object = LocalDate.parse(date, formatter);
        }
        catch(Exception exception) {
            return new Response("Invalid date");
        }
        return new Response(facade.confirm_shift(this.id, date_object, ShiftType.valueOf(shift_type.toUpperCase()), store));
    }

    public Response create_weekly_schedule(String first_day, String store, String morn_start, String morn_end, String eve_start, String eve_end) {
        LocalDate date_object;
        LocalTime morning_start, morning_end, evening_start, evening_end;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            date_object = LocalDate.parse(first_day, formatter);
        }
        catch(Exception exception) {
            return new Response("Invalid date");
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            morning_start = LocalTime.parse(morn_start, formatter);
            morning_end = LocalTime.parse(morn_end, formatter);
            evening_start = LocalTime.parse(eve_start, formatter);
            evening_end = LocalTime.parse(eve_end, formatter);
        }
        catch(Exception exception) {
            return new Response("Invalid shifts time");
        }

        return new Response(facade.create_weekly_schedule(this.id, date_object, store, morning_start, morning_end, evening_start, evening_end));
    }

    public Response assign_shift(String id, String date, String shift_type, String store, String role) {
        if (!shift_type.equalsIgnoreCase("morning") && !shift_type.equalsIgnoreCase("evening")) {
            return new Response("Invalid shift type");
        }
        LocalDate date_object;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            date_object = LocalDate.parse(date, formatter);
        }
        catch(Exception exception) {
            return new Response("Invalid date");
        }
        int id_num;
        JobType job;
        try {id_num = Integer.parseInt(id);} catch (Exception exception) {return new Response("Invalid id");}
        try {job = JobType.valueOf(role.toUpperCase()); } catch (Exception exception) {return new Response("Invalid role");}
        return new Response(facade.assign_shift(this.id, id_num, date_object, ShiftType.valueOf(shift_type.toUpperCase()), store, job));
    }

    public Response unassign_shift(String id, String date, String shift_type, String store, String role) {
        if (!shift_type.equalsIgnoreCase("morning") && !shift_type.equalsIgnoreCase("evening")) {
            return new Response("Invalid shift type");
        }
        LocalDate date_object;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            date_object = LocalDate.parse(date, formatter);
        }
        catch(Exception exception) {
            return new Response("Invalid date");
        }
        int id_num;
        JobType job;
        try {id_num = Integer.parseInt(id);} catch (Exception exception) {return new Response("Invalid id");}
        try {job = JobType.valueOf(role.toUpperCase()); } catch (Exception exception) {return new Response("Invalid role");}
        return new Response(facade.unassign_shift(this.id, id_num, date_object, ShiftType.valueOf(shift_type.toUpperCase()), store, job));
    }

    public Response limit_work(String id, String date, String shift_type, String store) {
        if (!shift_type.equalsIgnoreCase("morning") && !shift_type.equalsIgnoreCase("evening")) {
            return new Response("Invalid shift type");
        }
        LocalDate date_object;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            date_object = LocalDate.parse(date, formatter);
        }
        catch(Exception exception) {
            return new Response("Invalid date");
        }
        int id_num;
        try {id_num = Integer.parseInt(id);} catch (Exception exception) {return new Response("Invalid id");}
        return new Response(facade.limit_work(this.id, id_num, date_object, ShiftType.valueOf(shift_type.toUpperCase()), store));
    }

    public Response remove_worker_limit(String id, String date, String shift_type, String store) {
        if (!shift_type.equalsIgnoreCase("morning") && !shift_type.equalsIgnoreCase("evening")) {
            return new Response("Invalid shift type");
        }
        LocalDate date_object;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            date_object = LocalDate.parse(date, formatter);
        }
        catch(Exception exception) {
            return new Response("Invalid date");
        }
        int id_num;
        try {id_num = Integer.parseInt(id);} catch (Exception exception) {return new Response("Invalid id");}
        return new Response(facade.remove_worker_limit(this.id, id_num, date_object, ShiftType.valueOf(shift_type.toUpperCase()), store));
    }

    public Response show_shift_availability(String date, String shift_type, String store) {
        if (!shift_type.equalsIgnoreCase("morning") && !shift_type.equalsIgnoreCase("evening")) {
            return new Response("Invalid shift type");
        }
        LocalDate date_object;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            date_object = LocalDate.parse(date, formatter);
        }
        catch(Exception exception) {
            return new Response("Invalid date");
        }
        return new Response(facade.show_shift_availability(this.id, date_object, ShiftType.valueOf(shift_type.toUpperCase()), store));
    }

    public Response show_shift_assigned(String date, String shift_type, String store) {
        if (!shift_type.equalsIgnoreCase("morning") && !shift_type.equalsIgnoreCase("evening")) {
            return new Response("Invalid shift type");
        }
        LocalDate date_object;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            date_object = LocalDate.parse(date, formatter);
        }
        catch(Exception exception) {
            return new Response("Invalid date");
        }
        return new Response(facade.show_shift_assigned(this.id, date_object, ShiftType.valueOf(shift_type.toUpperCase()), store));
    }
    public boolean is_logged_in() {
        return id != -1;
    }

    public Response change_name(String old_name, String new_name) {
        return new Response(facade.change_name(this.id, old_name, new_name));
    }

    public Response change_bank_account(String old_bank_account, String new_bank_account) {
        int old_bank_num, new_bank_num;
        try {old_bank_num = Integer.parseInt(old_bank_account);} catch (Exception exception) {return new Response("Invalid old bank account");}
        try {new_bank_num = Integer.parseInt(new_bank_account);} catch (Exception exception) {return new Response("Invalid new bank account");}
        return new Response(facade.change_bank_account(this.id, old_bank_num, new_bank_num));
    }

    public Response change_family_status(String old_family_status, String new_family_status) {
        return new Response(facade.change_family_status(this.id, old_family_status, new_family_status));
    }

    public Response change_student(String old_student_status, String new_student_status) {
        boolean old_value, new_value;
        old_value = old_student_status.equalsIgnoreCase("yes");
        new_value = new_student_status.equalsIgnoreCase("yes");
        return new Response(facade.change_student(this.id, old_value, new_value));
    }

    public Response change_employee_salary(String employee_id, String old_salary, String new_salary) {
        int id_num, new_salary_num, old_salary_num;
        try {id_num = Integer.parseInt(employee_id);} catch (Exception exception) {return new Response("Invalid id");}
        try {old_salary_num = Integer.parseInt(old_salary);} catch (Exception exception) {return new Response("Invalid old salary");}
        try {new_salary_num = Integer.parseInt(new_salary);} catch (Exception exception) {return new Response("Invalid new salary");}
        return new Response(facade.change_employee_salary(this.id, id_num, old_salary_num, new_salary_num));
    }

    public Response change_employee_terms(String employee_id, String new_terms) {
        int id_num;
        try {id_num = Integer.parseInt(employee_id);} catch (Exception exception) {return new Response("Invalid id");}
        return new Response(facade.change_employee_terms(this.id, id_num, new_terms));
    }

    public Response confirm_monthly_salary(String employee_id, String bonus) {
        int id_num, bonus_num;
        try {id_num = Integer.parseInt(employee_id);} catch (Exception exception) {return new Response("Invalid id");}
        try {bonus_num = Integer.parseInt(bonus);} catch (Exception exception) {return new Response("Invalid id");}
        return new Response(facade.confirm_monthly_salary(this.id, id_num, bonus_num));
    }

    public boolean is_hr() {
        return facade.is_hr(this.id);
    }

    public Response show_personal_info() {
        return new Response(facade.show_personal_info(this.id));
    }

    public Response show_role_certifications() {
        return new Response(facade.show_role_certifications(this.id));
    }

    public Response show_assigned_stores() {
        return new Response(facade.show_assigned_stores(this.id));
    }

    public Response show_current_salary() {
        return new Response(facade.show_current_salary(this.id));
    }

    public void add_hr(Integer id, String name, Integer bank_account, double salary, String terms_of_employment, LocalDate employment_date, String family_status, boolean is_student, String password) {
        facade.add_hr(id, name, bank_account, salary, terms_of_employment, employment_date, family_status, is_student, password);
    }

    public Response show_employees() {
        return new Response(facade.show_employees(this.id));
    }

    public Response show_employee_info(String id) {
        int id_num;
        try {id_num = Integer.parseInt(id);} catch (Exception exception) {return new Response("Invalid id");}
        return new Response(facade.show_employee_info(this.id, id_num));
    }

    public Response load_data() {
        return new Response(facade.load_data());
    }

    public Response cancel_product(String product_id, String date, String shift_type, String store) {
        if (!shift_type.equalsIgnoreCase("morning") && !shift_type.equalsIgnoreCase("evening")) {
            return new Response("Invalid shift type");
        }
        LocalDate date_object;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            date_object = LocalDate.parse(date, formatter);
        }
        catch(Exception exception) {
            return new Response("Invalid date");
        }
        int product_id_num;
        try {product_id_num = Integer.parseInt(product_id);} catch (Exception exception) {return new Response("Invalid id");}
        return new Response(facade.cancel_product(this.id, product_id_num, date_object, ShiftType.valueOf(shift_type.toUpperCase()), store));
    }

}
