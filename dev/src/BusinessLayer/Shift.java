package BusinessLayer;

import java.time.LocalDate;
import java.util.List;

public class Shift {
    private LocalDate date;
    private Integer id;
    private shift_type type;
    private enum shift_type {
        MORNING,
        EVENING
    }
    private Worker shift_manager;
    private List<String> events;
    private String branch;

    public Shift(LocalDate date, Integer id, shift_type type, Worker shift_manager, List<String> events, String branch) {
        this.date = date;
        this.id = id;
        this.type = type;
        this.shift_manager = shift_manager;
        this.events = events;
        this.branch = branch;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getId() {
        return id;
    }

    public shift_type getType() {
        return type;
    }

    public Worker getShift_manager() {
        return shift_manager;
    }

    public List<String> getEvents() {
        return events;
    }

    public String getBranch() {
        return branch;
    }
}
