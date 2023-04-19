package HR.BusinessLayer;

import java.sql.Time;
import java.util.*;

public class ShiftController {

    private Map<String, Schedule> stores_schedules;
    private List<String> stores;
    private Map<String, List<Schedule>> schedules_history;

    public ShiftController() {
        stores_schedules = new HashMap<>();
        stores = new LinkedList<>();
        schedules_history = new HashMap<>();
    }

    public boolean store_exists(String store) {
        return stores.contains(store);
    }

    public String add_availability(Integer id, Date date_object, ShiftType type, String store) {
        if (!store_exists(store)) {
            return "Store doesn't exists";
        }
        return stores_schedules.get(store).add_availability(id, date_object, type);
    }

    public String remove_availability(Integer id, Date date_object, ShiftType type, String store) {
        if (!store_exists(store)) {
            return "Store doesn't exists";
        }
        return stores_schedules.get(store).remove_availability(id, date_object, type);
    }

    public String get_availability(Integer id, List<String> certified_stores) {
        String availability = "";
        for (String store: certified_stores) {
            availability = availability + store + ":\n";
            availability = availability + stores_schedules.get(store).get_availability(id);
        }
        return availability;
    }

    public String get_shifts(Integer id, List<String> certified_stores) {
        String shifts = "";
        for (String store: certified_stores) {
            shifts = shifts + store + ":\n";
            shifts = shifts + stores_schedules.get(store).get_shifts(id);
        }
        return shifts;

    }

    public String create_store(Integer id, String store) {
        if (store_exists(store)) {
            return "Store already exists";
        }
        stores.add(store);
        schedules_history.put(store, new LinkedList<>());
        return "";
    }

    public String remove_store(Integer id, String store) {
        if (store_exists(store)) {
            stores.add(store);
            stores_schedules.remove(store);
            return "";
        }
        return "Store doesn't exists";
    }

    public String confirm_shift(Date date_object, ShiftType shift, String store) {
        if (!store_exists(store)) {
            return "Store doesn't exists";
        }
        return stores_schedules.get(store).confirm_shift(date_object, shift);
    }

    public String create_weekly_schedule(Date date_object, String store, Time morn_start, Time morn_end, Time eve_start, Time eve_end) {
        if (!store_exists(store)) {
            return "Store doesn't exists";
        }
        if (stores_schedules.containsKey(store)) {
            schedules_history.get(store).add(stores_schedules.get(store));
        }
        stores_schedules.put(store, new Schedule(store, date_object, morn_start, morn_end, eve_start, eve_end));
        return "";
    }

    public String assign_shift(int id_num, Date date_object, ShiftType shift_type, String store, JobType role) {
        if (!store_exists(store)) {
            return "Store doesn't exists";
        }
        return stores_schedules.get(store).assign_shift(id_num, date_object, shift_type, role);
    }

    public String shifts_limit(List<String> certified_stores, int id, Date date_object, ShiftType shift_type) {
        int weekly_shifts_num = 0;
        for (String schedule: certified_stores) {
            int num = stores_schedules.get(schedule).shifts_limit(id, date_object, shift_type);
            if (num == -1) {
                return "Employee is already assigned to a shift on this day. 2 shifts in a day isn't allowed.";
            }
            weekly_shifts_num = weekly_shifts_num + num;
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
        return stores_schedules.get(store).unassign_shift(id_num, date_object, shift_type, job);
    }

    public String limit_work(int id_num, Date date_object, ShiftType shift_type, String store) {
        if (!store_exists(store)) {
            return "Store doesn't exists";
        }
        return stores_schedules.get(store).limit_work(id_num, date_object, shift_type);
    }

    public String remove_worker_limit(int id_num, Date date_object, ShiftType shift_type, String store) {
        if (!store_exists(store)) {
            return "Store doesn't exists";
        }
        return stores_schedules.get(store).remove_worker_limit(id_num, date_object, shift_type);
    }

    public List<Integer> show_shift_availability(Date date_object, ShiftType shift_type, String store) {
        return stores_schedules.get(store).show_shift_availability(date_object, shift_type);
    }
}
