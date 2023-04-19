package HR.ServiceLayer;

import HR.BusinessLayer.Facade;
import HR.BusinessLayer.JobType;
import HR.BusinessLayer.ShiftType;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeService {

    private Facade facade;
    private Integer id;

    public Response add_availability(String date, String shift_type, String store) {
        if (!shift_type.toLowerCase().equals("morning") && !shift_type.toLowerCase().equals("evening")) {
            return new Response("Invalid shift type");
        }
        Date date_object;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            date_object = dateFormat.parse(date);
        }
        catch(Exception exception) {
            return new Response("Invalid date");
        }
        return new Response(facade.add_availability(id, date_object, ShiftType.valueOf(shift_type), store));
    }

    public Response remove_availability(String date, String shift_type, String store) {
        if (!shift_type.toLowerCase().equals("morning") && !shift_type.toLowerCase().equals("evening")) {
            return new Response("Invalid shift type");
        }
        Date date_object;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            date_object = dateFormat.parse(date);
        }
        catch(Exception exception) {
            return new Response("Invalid date");
        }
        return new Response(facade.remove_availability(id, date_object, ShiftType.valueOf(shift_type), store));
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

    public Response logout(String id) {
        try {
            int id_num = Integer.parseInt(id);
            Response response = new Response(facade.logout(id_num));
            if (response.errorOccurred()) {
                return response;
            }
            this.id = null;
            return response;
        }
        catch (Exception exception) {
            return new Response("Invalid id");
        }
    }

    public Response add_employee(String id, String name, String bank_account, String salary, String terms_of_employment, String employment_date, String family_status, String is_student, String password) {
        int id_num, bank_account_num, salary_num;
        Date date_object;
        boolean student;
        try {id_num = Integer.parseInt(id);} catch (Exception exception) {return new Response("Invalid id");}
        try {bank_account_num = Integer.parseInt(bank_account);} catch (Exception exception) {return new Response("Invalid bank account");}
        try {salary_num = Integer.parseInt(salary);} catch (Exception exception) {return new Response("Invalid salary");}
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            date_object = dateFormat.parse(employment_date);
        }
        catch(Exception exception) {
            return new Response("Invalid date");
        }
        if (is_student.toLowerCase().equals("yes")) {
            student = true;
        }
        else {
            student = false;
        }
        if (!name.matches(".*\\d+.*")) {
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
        if (!shift_type.toLowerCase().equals("morning") && !shift_type.toLowerCase().equals("evening")) {
            return new Response("Invalid shift type");
        }
        Date date_object;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            date_object = dateFormat.parse(date);
        }
        catch(Exception exception) {
            return new Response("Invalid date");
        }
        return new Response(facade.confirm_shift(this.id, date_object, ShiftType.valueOf(shift_type), store));
    }

    public Response create_weekly_schedule(String first_day, String store, String morn_start, String morn_end, String eve_start, String eve_end) {
        Date date_object;
        Time morning_start, morning_end, evening_start, evening_end;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            date_object = dateFormat.parse(first_day);
        }
        catch(Exception exception) {
            return new Response("Invalid date");
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date date1 = dateFormat.parse(morn_start);
            morning_start = new Time(date1.getTime());
            Date date2 = dateFormat.parse(morn_end);
            morning_end = new Time(date2.getTime());
            Date date3 = dateFormat.parse(eve_start);
            evening_start = new Time(date3.getTime());
            Date date4 = dateFormat.parse(eve_end);
            evening_end = new Time(date4.getTime());
        }
        catch(Exception exception) {
            return new Response("Invalid shifts time");
        }

        return new Response(facade.create_weekly_schedule(this.id, date_object, store, morning_start, morning_end, evening_start, evening_end));
    }

    public Response assign_shift(String id, String date, String shift_type, String store, String role) {
        if (!shift_type.toLowerCase().equals("morning") && !shift_type.toLowerCase().equals("evening")) {
            return new Response("Invalid shift type");
        }
        Date date_object;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            date_object = dateFormat.parse(date);
        }
        catch(Exception exception) {
            return new Response("Invalid date");
        }
        int id_num;
        JobType job;
        try {id_num = Integer.parseInt(id);} catch (Exception exception) {return new Response("Invalid id");}
        try {job = JobType.valueOf(role.toUpperCase()); } catch (Exception exception) {return new Response("Invalid role");}
        return new Response(facade.assign_shift(this.id, id_num, date_object, ShiftType.valueOf(shift_type), store, job));
    }

    public Response unassign_shift(String id, String date, String shift_type, String store, String role) {
        if (!shift_type.toLowerCase().equals("morning") && !shift_type.toLowerCase().equals("evening")) {
            return new Response("Invalid shift type");
        }
        Date date_object;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            date_object = dateFormat.parse(date);
        }
        catch(Exception exception) {
            return new Response("Invalid date");
        }
        int id_num;
        JobType job;
        try {id_num = Integer.parseInt(id);} catch (Exception exception) {return new Response("Invalid id");}
        try {job = JobType.valueOf(role.toUpperCase()); } catch (Exception exception) {return new Response("Invalid role");}
        return new Response(facade.unassign_shift(this.id, id_num, date_object, ShiftType.valueOf(shift_type), store, job));
    }

    public Response limit_work(String id, String date, String shift_type, String store) {
        if (!shift_type.toLowerCase().equals("morning") && !shift_type.toLowerCase().equals("evening")) {
            return new Response("Invalid shift type");
        }
        Date date_object;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            date_object = dateFormat.parse(date);
        }
        catch(Exception exception) {
            return new Response("Invalid date");
        }
        int id_num;
        try {id_num = Integer.parseInt(id);} catch (Exception exception) {return new Response("Invalid id");}
        return new Response(facade.limit_work(this.id, id_num, date_object, ShiftType.valueOf(shift_type), store));
    }

    public Response remove_worker_limit(String id, String date, String shift_type, String store) {
        if (!shift_type.toLowerCase().equals("morning") && !shift_type.toLowerCase().equals("evening")) {
            return new Response("Invalid shift type");
        }
        Date date_object;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            date_object = dateFormat.parse(date);
        }
        catch(Exception exception) {
            return new Response("Invalid date");
        }
        int id_num;
        try {id_num = Integer.parseInt(id);} catch (Exception exception) {return new Response("Invalid id");}
        return new Response(facade.remove_worker_limit(this.id, id_num, date_object, ShiftType.valueOf(shift_type), store));
    }

    public Response show_shift_availability(String date, String shift_type, String store) {
        if (!shift_type.toLowerCase().equals("morning") && !shift_type.toLowerCase().equals("evening")) {
            return new Response("Invalid shift type");
        }
        Date date_object;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            date_object = dateFormat.parse(date);
        }
        catch(Exception exception) {
            return new Response("Invalid date");
        }
        return new Response(facade.show_shift_availability(this.id, date_object, ShiftType.valueOf(shift_type), store));
    }
}
