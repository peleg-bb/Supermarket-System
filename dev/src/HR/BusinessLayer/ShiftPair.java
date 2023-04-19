package HR.BusinessLayer;

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
}
