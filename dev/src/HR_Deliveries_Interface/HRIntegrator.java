package HR_Deliveries_Interface;

import java.sql.Timestamp;
import java.util.List;

public interface HRIntegrator {
    boolean checkAvailability(Timestamp arrivalTime);
    List<String> getAvailableDrivers(Timestamp startTime, Timestamp endTime);
    boolean assignShifts(String driverId, Timestamp startTime, Timestamp endTime);

}
