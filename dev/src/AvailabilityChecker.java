import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

public interface AvailabilityChecker {
    boolean checkAvailability(Timestamp arrivalTime);
    List<String> getAvailableDrivers(Timestamp startTime, Timestamp endTime);

}
