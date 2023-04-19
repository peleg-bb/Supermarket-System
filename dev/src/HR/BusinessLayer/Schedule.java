package HR.BusinessLayer;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Schedule {
    private String store;
    private Map<ShiftPair, Shift> shifts;

    public Schedule(String store, Date first_day, Time morn_start, Time morn_end, Time eve_start, Time eve_end) {
        this.store = store;
        initialize_week_shifts(first_day, morn_start, morn_end, eve_start, eve_end);
    }

//    public String add_shift(Date date_object, ShiftType type) {
//        ShiftPair shiftPair = get_shift(date_object, type);
//        if (shiftPair != null) {
//            return "Shift already exists";
//        }
//
//        shifts.put(new ShiftPair(date_object, type), new Shift());
//        return "";
//    }
//
//    public String remove_shift(Date date_object, ShiftType type) {
//        ShiftPair shiftPair = get_shift(date_object, type);
//        if (shiftPair != null) {
//            shifts.remove(shiftPair);
//            return "";
//        }
//        return "Shift doesn't exist";
//    }
    private void initialize_week_shifts(Date first_day, Time morn_start, Time morn_end, Time eve_start, Time eve_end) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(first_day);
        for (int i = 0; i < 7; i++) {
            shifts.put(new ShiftPair(first_day, ShiftType.MORNING), new Shift(morn_start, morn_end));
            shifts.put(new ShiftPair(first_day, ShiftType.EVENING), new Shift(eve_start, eve_end));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            first_day = calendar.getTime();
        }
    }

    public String add_availability(Integer id, Date date_object, ShiftType type) {
        ShiftPair shiftPair = get_shift(date_object, type);
        if (shiftPair != null) {
            return shifts.get(shiftPair).add_availability(id);
        }
        return "Shift doesn't exists";
    }

    private ShiftPair get_shift(Date date_object, ShiftType type) {
        for (ShiftPair shift_pair: shifts.keySet()) {
            if (shift_pair.getType() == type && shift_pair.getDate() == date_object) {
                return shift_pair;
            }
        }
        return null;
    }

    public String remove_availability(Integer id, Date date_object, ShiftType type) {
        ShiftPair shiftPair = get_shift(date_object, type);
        if (shiftPair != null) {
            return shifts.get(shiftPair).remove_availability(id);
        }
        return "Shift doesn't exists";
    }

    public String get_availability(Integer id) {
        String availability = "";
        for (ShiftPair pair: shifts.keySet()) {
            if (shifts.get(pair).is_available(id)) {
                availability = availability + pair.getDate().toString() + " - " + pair.getType().toString() + "\n";
            }
        }
        return availability;
    }

    public String get_shifts(Integer id) {
        String shifts_list = "";
        for (ShiftPair pair: shifts.keySet()) {
            String job = shifts.get(pair).is_assigned(id);
            if (!job.equals("")) {
                shifts_list = shifts_list + pair.getDate().toString() + " - " + pair.getType().toString() + ", as " + job + "\n";
            }
        }
        return shifts_list;
    }

    public String confirm_shift(Date date_object, ShiftType shift) {
        return shifts.get(get_shift(date_object, shift)).confirm_shift();
    }

    public String assign_shift(int id_num, Date date_object, ShiftType shift_type, JobType role) {
        return shifts.get(get_shift(date_object, shift_type)).assign_shift(id_num, role);
    }

    public String unassign_shift(int id_num, Date date_object, ShiftType shift_type, JobType job) {
        return shifts.get(get_shift(date_object, shift_type)).unassign_shift(id_num, job);
    }

    public String limit_work(int id_num, Date date_object, ShiftType shift_type) {
        return shifts.get(get_shift(date_object, shift_type)).limit_work(id_num);
    }

    public String remove_worker_limit(int id_num, Date date_object, ShiftType shift_type) {
        return shifts.get(get_shift(date_object, shift_type)).remove_worker_limit(id_num);
    }

    public List<Integer> show_shift_availability(Date date_object, ShiftType shift_type) {
        return shifts.get(get_shift(date_object, shift_type)).show_shift_availability();
    }

    public int shifts_limit(int id, Date date_object, ShiftType shift_type) {
        int counter = 0;
        for (ShiftPair pair: shifts.keySet()) {
            if (!shifts.get(pair).is_assigned(id).equals("")) {
                if (pair.getDate() == date_object) {
                    return -1;
                }
                counter = counter + 1;
            }
        }
        return counter;
    }
}
