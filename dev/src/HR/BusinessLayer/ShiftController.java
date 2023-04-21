package HR.BusinessLayer;

import HR.DataAccessLayer.ShiftDAO;
import HR_Deliveries_Interface.HRIntegrator;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

public class ShiftController implements HRIntegrator {

    private Map<String, List<Schedule>> stores_schedules;
    private List<String> stores;
    private Map<String, List<Schedule>> schedules_history;
    private ShiftDAO shiftDAO;


    public ShiftController() {
        stores_schedules = new HashMap<>();
        stores = new LinkedList<>();
        stores.add("drivers");
        schedules_history = new HashMap<>();
        shiftDAO = new ShiftDAO();
    }

    public boolean store_exists(String store) {
        return stores.contains(store);
    }

    public String add_availability(Integer id, Date date_object, ShiftType type, String store) {
        if (!store_exists(store)) {
            return "Store doesn't exists";
        }
        if (stores_schedules.containsKey(store)) {
            return get_schedule(store, date_object).add_availability(id, date_object, type);
        }
        return "No schedule available for that date";
    }

    public String remove_availability(Integer id, Date date_object, ShiftType type, String store) {
        if (!store_exists(store)) {
            return "Store doesn't exists";
        }
        if (stores_schedules.containsKey(store)) {
            return get_schedule(store, date_object).remove_availability(id, date_object, type);
        }
        return "No schedule available for that date";
    }

    public String get_availability(Integer id, List<String> certified_stores) {
        String availability = "";
        for (String store: certified_stores) {
            if (stores_schedules.containsKey(store)) {
                availability = availability + store + ":\n";
                for (Schedule schedule : stores_schedules.get(store)) {
                    availability = availability + schedule.get_availability(id);
                }
            }
        }
        return availability;
    }

    public String get_shifts(Integer id, List<String> certified_stores) {
        String shifts = "";
        for (String store: certified_stores) {
            if (stores_schedules.containsKey(store)) {
                shifts = shifts + store + ":\n";
                for (Schedule schedule : stores_schedules.get(store)) {
                    shifts = shifts + schedule.get_shifts(id);
                }
            }
        }
        return shifts;

    }

    public String create_store(String store) {
        stores.add(store);
        schedules_history.put(store, new LinkedList<>());
        return "";
    }

    public String remove_store(String store) {
        if (store_exists(store)) {
            stores.remove(store);
            stores_schedules.remove(store);
            return "";
        }
        return "Store doesn't exists";
    }

    public String confirm_shift(Date date_object, ShiftType shift, String store) {
        if (!store_exists(store)) {
            return "Store doesn't exists";
        }
        return get_schedule(store, date_object).confirm_shift(date_object, shift);
    }

    public String create_weekly_schedule(Date date_object, String store, Time morn_start, Time morn_end, Time eve_start, Time eve_end) {
        if (!store_exists(store)) {
            return "Store doesn't exists";
        }
        if (stores_schedules.containsKey(store)) {
            handle_schedules(store);
            stores_schedules.get(store).add(new Schedule(store, date_object, morn_start, morn_end, eve_start, eve_end, shiftDAO));
        }
        else {
            stores_schedules.put(store, new LinkedList<>());
            stores_schedules.get(store).add(new Schedule(store, date_object, morn_start, morn_end, eve_start, eve_end, shiftDAO));
        }
        return "";
    }

    private void handle_schedules(String store) {
        Calendar calendar = Calendar.getInstance();
        for (Schedule schedule: stores_schedules.get(store)) {
            if (!schedule.current_week(calendar.get(calendar.WEEK_OF_YEAR))) {
                schedules_history.get(store).add(schedule);
                stores_schedules.get(store).remove(schedule);
            }
        }
    }

    private Schedule get_schedule(String store, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (!stores_schedules.containsKey(store)) {
            return null;
        }
        for (Schedule schedule: stores_schedules.get(store)) {
            if (schedule.current_week(calendar.get(calendar.WEEK_OF_YEAR))) {
                return schedule;
            }
        }
        return null;
    }

    public String assign_shift(int id_num, Date date_object, ShiftType shift_type, String store, JobType role) {
        if (!store_exists(store)) {
            return "Store doesn't exists";
        }
        if (get_schedule(store, date_object) != null) {
            return get_schedule(store, date_object).assign_shift(id_num, date_object, shift_type, role);
        }
        return "No schedule available for that date";
    }

    public String shifts_limit(List<String> certified_stores, int id, Date date_object, ShiftType shift_type) {
        int weekly_shifts_num = 0;
        for (String schedule: certified_stores) {
            if (get_schedule(schedule, date_object) != null) {
                int num = get_schedule(schedule, date_object).shifts_limit(id, date_object, shift_type);

                if (num == -1) {
                    return "Employee is already assigned to a shift on this day. 2 shifts in a day isn't allowed.";
                }
                weekly_shifts_num = weekly_shifts_num + num;
            }
        }
        if (weekly_shifts_num >= 6) {
            return "Employee is already assigned to 6 shifts this week. 6 is the limit.";
        }
        return "";
    }

    public String unassign_shift(int id_num, Date date_object, ShiftType shift_type, String store, JobType job) {
        if (!store_exists(store)) {
            return "Store doesn't exists";
        }
        if (get_schedule(store, date_object) != null) {
            return get_schedule(store, date_object).unassign_shift(id_num, date_object, shift_type, job);
        }
        return "No schedule available for that date";
    }

    public String limit_work(int id_num, Date date_object, ShiftType shift_type, String store) {
        if (!store_exists(store)) {
            return "Store doesn't exists";
        }
        if (get_schedule(store, date_object) != null) {
            return get_schedule(store, date_object).limit_work(id_num, date_object, shift_type);
        }
        return "No schedule available for that date";
    }

    public String remove_worker_limit(int id_num, Date date_object, ShiftType shift_type, String store) {
        if (!store_exists(store)) {
            return "Store doesn't exists";
        }
        if (get_schedule(store, date_object) != null) {
            return get_schedule(store, date_object).remove_worker_limit(id_num, date_object, shift_type);
        }
        return "No schedule available for that date";
    }

    public List<Integer> show_shift_availability(Date date_object, ShiftType shift_type, String store) {
        if (get_schedule(store, date_object) != null) {
            return get_schedule(store, date_object).show_shift_availability(date_object, shift_type);
        }
        return new LinkedList<>();
    }

    public boolean has_future_shifts(List<String> certified_stores, int id_num) {
        for (String store: certified_stores) {
            if (stores_schedules.containsKey(store)) {
                for (Schedule schedule : stores_schedules.get(store)) {
                    if (schedule.has_future_shifts(Calendar.getInstance().getTime(), id_num)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean has_future_shifts_role(List<String> certified_stores, JobType role, int id_num) {
        for (String store: certified_stores) {
            if (stores_schedules.containsKey(store)) {
                for (Schedule schedule : stores_schedules.get(store)) {
                    if (schedule.has_future_shifts_role(Calendar.getInstance().getTime(), role, id_num)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean has_future_shifts(String store, int id_num) {
        if(!stores_schedules.containsKey(store)) {
            return false;
        }
        for (Schedule schedule : stores_schedules.get(store)) {
            if (schedule.has_future_shifts(Calendar.getInstance().getTime(), id_num)) {
                return true;
            }
        }
        return false;
    }

    public double get_hours(Date date_object, ShiftType shift_type, String store) {
        if (get_schedule(store, Calendar.getInstance().getTime()) != null) {
            return get_schedule(store, date_object).get_hours(date_object, shift_type);
        }
        return 0;
    }

    @Override
    public boolean checkAvailability(String store, Timestamp arrivalTime) {
        if (get_schedule(store, arrivalTime) != null) {
            return get_schedule(store, arrivalTime).check_availability(arrivalTime);
        }
        return false;
    }

    @Override
    public List<String> getAvailableDrivers(Timestamp startTime, Timestamp endTime) {
        if (get_schedule("drivers", startTime) != null) {
            return get_schedule("drivers", startTime).get_available_drivers(startTime, endTime);
        }
        return new LinkedList<>();
    }

    @Override
    public boolean assignShifts(String driverId, Timestamp startTime, Timestamp endTime) {
        int id_num;
        try {id_num = Integer.parseInt(driverId);} catch (Exception exception) {return false;}
        if (get_schedule("drivers", startTime) != null) {
            return get_schedule("drivers", startTime).assign_drivers(id_num, startTime, endTime);
        }
        return false;
    }

    public String load_data(List<String> stores) {
        this.stores = stores;
        Map<ShiftPair, Shift> shifts = shiftDAO.get_shifts();
        if (shifts.isEmpty()) {
            return "";
        }
        Map<Integer, Map<String, Map<ShiftPair, Shift>>> organized_shifts = organizeDatesIntoWeeks(shifts);
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        int current_week = calendar.get(calendar.WEEK_OF_YEAR);
        for (Integer num: organized_shifts.keySet()) {
            Map<String, Map<ShiftPair, Shift>> week_schedules = organized_shifts.get(num);
            for (String store: week_schedules.keySet()) {
                Schedule schedule = new Schedule(store, week_schedules.get(store), shiftDAO);
                if (num >= current_week) {
                    if (stores_schedules.containsKey(store)) {
                        stores_schedules.get(store).add(schedule);
                    }
                    else {
                        stores_schedules.put(store, new LinkedList<>());
                        stores_schedules.get(store).add(schedule);
                    }
                }
                else {
                    if (!schedules_history.containsKey(store)) {
                        schedules_history.put(store, new LinkedList<>());
                        schedules_history.get(store).add(schedule);
                    }
                    else {
                        schedules_history.get(store).add(schedule);
                    }
                }
            }
        }
        return "";
    }

    public static Map<Integer, Map<String, Map<ShiftPair, Shift>>> organizeDatesIntoWeeks(Map<ShiftPair, Shift> shifts) {
        Map<Integer, Map<String, Map<ShiftPair, Shift>>> weeksMap = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        for (ShiftPair date : shifts.keySet()) {
            String store = shifts.get(date).get_store();
            calendar.setTime(date.getDate());
            int weekNumber = calendar.get(calendar.WEEK_OF_YEAR);
            if (weeksMap.containsKey(weekNumber)) {
                if (weeksMap.get(weekNumber).containsKey(store)) {
                    weeksMap.get(weekNumber).get(store).put(date, shifts.get(date));
                }
                else {
                    Map<ShiftPair, Shift> curr = new HashMap<>();
                    curr.put(date, shifts.get(date));
                    weeksMap.get(weekNumber).put(store, curr);
                }
            }
            else {
                weeksMap.put(weekNumber, new HashMap<>());
                if (weeksMap.get(weekNumber).containsKey(store)) {
                    weeksMap.get(weekNumber).get(store).put(date, shifts.get(date));
                }
                else {
                    Map<ShiftPair, Shift> curr = new HashMap<>();
                    curr.put(date, shifts.get(date));
                    weeksMap.get(weekNumber).put(store, curr);
                }
            }
        }
        return weeksMap;
    }

    public String cancel_product(int id, int product_id_num, Date date_object, ShiftType type, String store) {
        if (get_schedule(store, date_object) != null) {
            return get_schedule(store, date_object).cancel_product(id, product_id_num, date_object, type);
        }
        return "There isn't a schedule for that date";
    }
}
