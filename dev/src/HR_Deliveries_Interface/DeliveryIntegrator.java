package HR_Deliveries_Interface;

import Deliveries.BusinessLayer.DeliveryStop;

import java.sql.Timestamp;
import java.util.Set;

public interface DeliveryIntegrator {
    Set<DeliveryStop> getDeliverygetDeliveryByArrivalTime(Timestamp arrivalTime, String store);
}
