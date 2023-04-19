import java.sql.Timestamp;

public interface ShiftAssigner {
    boolean assignShifts(String driverId, Timestamp startTime, Timestamp endTime);
}
