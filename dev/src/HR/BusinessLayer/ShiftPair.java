package HR.BusinessLayer;

import java.time.ZoneId;
import java.util.Date;

public class ShiftPair {
    private Date date;
    private ShiftType type;


    public ShiftPair(Date date, ShiftType type) {
        this.date = date;
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public ShiftType getType() {
        return type;
    }

    public boolean equals(Date date, ShiftType type) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().equals(this.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) && type == this.type;
    }
}
