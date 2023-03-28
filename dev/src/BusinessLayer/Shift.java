package BusinessLayer;

import java.time.LocalDate;
import java.util.List;
import java.util.Date;


public class Shift {
    private LocalDate date;
    private String name;
    private shift_type type;
    public enum shift_type {
        MORNING,
        EVENING
    }
    private Integer shift_manager;
    private List<String> events;
    private String branch;

    public Shift(LocalDate date, String name, shift_type type, Integer shift_manager, List<String> events, String branch) {
        this.date = date;
        this.name = name;
        this.type = type;
        this.shift_manager = shift_manager;
        this.events = events;
        this.branch = branch;
    }

    public shift_type getType() {
        return type;
    }

    public Integer getShift_manager() {
        return shift_manager;
    }

    public void setShift_manager(Integer id) {
        shift_manager = id;
    }
}
