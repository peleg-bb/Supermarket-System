package HR.DataAccessLayer;

import HR.BusinessLayer.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ShiftDAO {
    private final Connect conn = Connect.getInstance();
    public ShiftDAO() {}



    public String add_availability(Integer id, LocalDate date_object, ShiftType type, String store) {
        try{
            int dayOfMonth = date_object.getDayOfMonth();
            int month = date_object.getMonthValue();
            int year = date_object.getYear();
            conn.executeUpdate("INSERT INTO Availability (store, shiftType, day, month, year, employeeID, availabilityOrConstraint) VALUES(?,?,?,?,?,?,?)", store, type.toString(), dayOfMonth, month, year, id, 1);
            return "";
        } catch (SQLException e) {
            return "Employee with id " + id + " is already available in this date";
        }
    }

    public String remove_availability(Integer id, LocalDate date_object, ShiftType type, String store) {
        try{
            int dayOfMonth = date_object.getDayOfMonth();
            int month = date_object.getMonthValue();
            int year = date_object.getYear();
            conn.executeUpdate("DELETE FROM Availability WHERE employeeID = " + id + " AND store = '" + store + "' AND day = '" + dayOfMonth + "' AND month = '" + month + "' AND year = '" + year + "' AND shiftType = '" + type.toString() + "'");
            return "";
        } catch (SQLException e) {
            return "Employee with id " + id + " is not available in this date";
        }
    }

    public String limit_work(int id_num, LocalDate date_object, ShiftType shift_type, String store) {
        try{
            int dayOfMonth = date_object.getDayOfMonth();
            int month = date_object.getMonthValue();
            int year = date_object.getYear();
            conn.executeUpdate("INSERT INTO Availability (store, shiftType, day, month, year, employeeID, availabilityOrConstraint) VALUES(?,?,?,?,?,?,?)", store, shift_type.toString(), dayOfMonth, month, year, id_num, 0);
            return "";
        } catch (SQLException e) {
            return "Employee with id " + id_num + " is already assigned / limited in this date.";
        }
    }

    public String remove_worker_limit(int id_num, LocalDate date_object, ShiftType shift_type, String store) {
        try{
            int dayOfMonth = date_object.getDayOfMonth();
            int month = date_object.getMonthValue();
            int year = date_object.getYear();
            conn.executeUpdate("DELETE FROM Availability WHERE employeeID = " + id_num + " AND store = '" + store + "' AND day = '" + dayOfMonth + "' AND month = '" + month + "' AND year = '" + year + "' AND shiftType = '" + shift_type.toString() + "'");
            return "";
        } catch (SQLException e) {
            return "Employee with id " + id_num + " is not available in this date";
        }
    }

    public String assign_shift(int id_num, LocalDate date_object, ShiftType shift_type, JobType role, String store) {
        try{
            int dayOfMonth = date_object.getDayOfMonth();
            int month = date_object.getMonthValue();
            int year = date_object.getYear();
            conn.executeUpdate("INSERT INTO EmployeesInShift (store, shiftType, day, month, year, employeeID, job) VALUES(?,?,?,?,?,?,?)", store, shift_type.toString(), dayOfMonth, month, year, id_num, role.toString());
            return "";
        } catch (SQLException e) {
            return "Employee with id " + id_num + " is already assigned to this shift";
        }
    }

    public String unassign_shift(int id_num, LocalDate date_object, ShiftType shift_type, JobType job, String store) {
        try{
            int dayOfMonth = date_object.getDayOfMonth();
            int month = date_object.getMonthValue();
            int year = date_object.getYear();
            conn.executeUpdate("DELETE FROM EmployeesInShift WHERE employeeID = " + id_num + " AND store = '" + store + "' AND day = '" + dayOfMonth + "' AND month = '" + month + "' AND year = '" + year + "' AND shiftType = '" + shift_type.toString() + "' AND job = '" + job.toString() + "'");
            return "";
        } catch (SQLException e) {
            return "Employee with id " + id_num + " is not assigned to this date";
        }
    }

    public String confirm_shift(LocalDate date_object, ShiftType shift, String store) {
        try{
            int dayOfMonth = date_object.getDayOfMonth();
            int month = date_object.getMonthValue();
            int year = date_object.getYear();
            conn.executeUpdate("UPDATE Shifts SET confirmed = " + 1 + " WHERE store = '" + store + "' AND day = '" + dayOfMonth + "' AND month = '" + month + "' AND year = '" + year + "' AND shiftType = '" + shift.toString() + "'");
            return "";
        } catch (SQLException e) {
            return "Couldn't update the data base";
        }
    }

    public void create_shift(LocalDate first_day, ShiftType shift_type, LocalTime morn_start, LocalTime morn_end, String store) {
        try{
            int dayOfMonth = first_day.getDayOfMonth();
            int month = first_day.getMonthValue();
            int year = first_day.getYear();
            conn.executeUpdate("INSERT INTO Shifts (store, shiftType, day, month, year, start, end, confirmed) VALUES(?,?,?,?,?,?,?,?)", store, shift_type.toString(), dayOfMonth, month, year, morn_start.toString(), morn_end.toString(), 0);
        } catch (SQLException ignored) {
        }
    }

    public Map<ShiftPair, Shift> get_shifts() {
        try {
            Map<ShiftPair, Shift> shifts_objects = new HashMap<>();
            List<HashMap<String, Object>> Shifts = conn.executeQuery("SELECT * FROM Shifts");
            for (HashMap<String, Object> record: Shifts) {
                Map<ShiftPair, Shift> rec = shift_record(record);
                Map.Entry<ShiftPair, Shift> firstEntry = rec.entrySet().iterator().next();
                ShiftPair key = firstEntry.getKey();
                Shift value = firstEntry.getValue();
                shifts_objects.put(key, value);
            }
            return shifts_objects;
        }
        catch (SQLException e) {
            return null;
        }
    }

    private Map<ShiftPair, Shift> shift_record(HashMap<String, Object> personalDetails) {
        String store = (String) personalDetails.get("store");
        String shiftType = (String) personalDetails.get("shiftType");
        String day = (String) personalDetails.get("day");
        String month = (String) personalDetails.get("month");
        String year = (String) personalDetails.get("year");
        String start = (String) personalDetails.get("start");
        String end = (String) personalDetails.get("end");
        Integer confirmed = (Integer) personalDetails.get("confirmed");
        Shift shift = new Shift(store, LocalTime.parse(start), LocalTime.parse(end));
        if (confirmed == 1) {
            shift.confirm_shift();
        }
        List<Integer> available_employees = shift_available(store, shiftType, day, month, year);
        Map<JobType, List<Integer>> employees = shift_employees(store, shift, day, month, year);
        Map<Integer, Integer> events = shift_events(store, shift, day, month, year);
        List<Integer> manager_constraints = shift_manager_constraints(store, shiftType, day, month, year);
        shift.set_available(available_employees);
        shift.set_constraints(manager_constraints);
        shift.set_employees(employees);
        shift.initialize_roles();
        shift.set_events(events);
        LocalDate localDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        ShiftPair pair = new ShiftPair(localDate, ShiftType.valueOf(shiftType));
        Map<ShiftPair, Shift> output = new HashMap<>();
        output.put(pair, shift);
        return output;
    }

    private List<Integer> shift_manager_constraints(String store, String shiftType, String day, String month, String year) {
        try {
            List<Integer> output = new LinkedList<>();
            List<HashMap<String, Object>> shift_manager_constraints = conn.executeQuery("SELECT * FROM Availability WHERE store = '" + store + "' AND day = '" + day + "' AND month = '" + month + "' AND year = '" + year + "' AND shiftType = '" + shiftType + "' AND availabilityOrConstraint = 0");
            for (HashMap<String, Object> record: shift_manager_constraints) {
                Integer id = (Integer) record.get("employeeID");
                output.add(id);
            }
            return output;
        }
        catch (SQLException e) {
            return null;
        }
    }

    private Map<Integer, Integer> shift_events(String store, Shift shift, String day, String month, String year) {
        try {
            Map<Integer, Integer> output = new HashMap<>();
            List<HashMap<String, Object>> shift_events = conn.executeQuery("SELECT * FROM ShiftEvents WHERE store = '" + store + "' AND day = '" + day + "' AND month = '" + month + "' AND year = '" + year + "' AND shiftType = '" + shift + "'");
            for (HashMap<String, Object> record: shift_events) {
                Integer id = (Integer) record.get("employeeId");
                Integer product_id = (Integer) record.get("productId");
                output.put(id, product_id);
            }
            return output;
        }
        catch (SQLException e) {
            return null;
        }
    }

    private Map<JobType, List<Integer>> shift_employees(String store, Shift shift, String day, String month, String year) {
        try {
            Map<JobType, List<Integer>> output = new HashMap<>();
            List<HashMap<String, Object>> shift_events = conn.executeQuery("SELECT * FROM EmployeesInShift WHERE store = '" + store + "' AND day = '" + day + "' AND month = '" + month + "' AND year = '" + year + "' AND shiftType = '" + shift + "'");
            for (HashMap<String, Object> record: shift_events) {
                String Job = (String) record.get("job");
                Integer employeeID = (Integer) record.get("employeeID");
                if (output.containsKey(JobType.valueOf(Job))) {
                    output.get(JobType.valueOf(Job)).add(employeeID);
                }
                else {
                    output.put(JobType.valueOf(Job), new LinkedList<>());
                    output.get(JobType.valueOf(Job)).add(employeeID);
                }
            }
            return output;
        }
        catch (SQLException e) {
            return null;
        }
    }

    private List<Integer> shift_available(String store, String shiftType, String day, String month, String year) {
        try {
            List<Integer> output = new LinkedList<>();
            List<HashMap<String, Object>> shift_manager_constraints = conn.executeQuery("SELECT * FROM Availability WHERE store = '" + store + "' AND day = '" + day + "' AND month = '" + month + "' AND year = '" + year + "' AND shiftType = '" + shiftType + "' AND availabilityOrConstraint = 1");
            for (HashMap<String, Object> record: shift_manager_constraints) {
                Integer id = (Integer) record.get("employeeID");
                output.add(id);
            }
            return output;
        }
        catch (SQLException e) {
            return null;
        }
    }

    public String cancel_product(int id, int product_id_num, LocalDate date_object, ShiftType type, String store) {
        try{
            int dayOfMonth = date_object.getDayOfMonth();
            int month = date_object.getMonthValue();
            int year = date_object.getYear();
            conn.executeUpdate("INSERT INTO ShiftEvents (store, shiftType, day, month, year, employeeId, productId) VALUES(?,?,?,?,?,?,?)", store, type.toString(), dayOfMonth, month, year, id, product_id_num);
            return "";
        } catch (SQLException e) {
            return "Couldn't update the database";
        }
    }
}
