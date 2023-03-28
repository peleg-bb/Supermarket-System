package BusinessLayer;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

public class Schedules {
    private Map<String, BranchSchedule> branchScheduleMap;

    public Schedules() {
        branchScheduleMap = new HashMap<>();
    }

    public Map<String, BranchSchedule> getBranchScheduleMap() {
        return branchScheduleMap;
    }

    public void add_schedule(String branch, BranchSchedule schedule) {
        branchScheduleMap.put(branch, schedule);
    }

    public BranchSchedule getSchedule(String branch) {
        return branchScheduleMap.get(branch);
    }

    public Map<String, BranchSchedule> getSchedules() {
        return branchScheduleMap;
    }

    public boolean assign_shift_manager(LocalDate date, Shift.shift_type type, String branch, Integer id) {
        return branchScheduleMap.get(branch).assign_shift_manager(date, type, id);
    }
}
