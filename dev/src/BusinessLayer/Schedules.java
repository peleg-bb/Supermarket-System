package BusinessLayer;

import java.util.HashMap;
import java.util.Map;

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


}
