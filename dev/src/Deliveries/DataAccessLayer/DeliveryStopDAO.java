package Deliveries.DataAccessLayer;

import Deliveries.BusinessLayer.DeliveryStop;
import HR.DataAccessLayer.Connect;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeliveryStopDAO {
    private final Connect conn;
    public DeliveryStopDAO() {
        conn = Connect.getInstance();
    }

    public Set<DeliveryStop> loadData() {
        Set<DeliveryStop> stops = new HashSet<>();
        try {
            List<HashMap<String, Object>> stopDetails = conn.executeQuery("SELECT * FROM DeliveryStops");
            for (HashMap<String, Object> stopRecord : stopDetails) {
                DeliveryStop stop = getStop(stopRecord);
                stops.add(stop);
            }
            return stops;
        } catch (SQLException exception) {
            return null;
        }
    }

        private DeliveryStop getStop(HashMap<String, Object> deliveryStopRecord) {
            int stopID = (Integer) deliveryStopRecord.get("stop_id");
            String origin = (String) deliveryStopRecord.get("origin_name");
            String destination = (String) deliveryStopRecord.get("destination_name");
            String truckType = (String) deliveryStopRecord.get("truck_type");
//            String status = (String) deliveryStopRecord.get("status");
//            String formID= (String) deliveryStopRecord.get("form_id");
            HashMap<String, Integer> items = new HashMap<>();
            return new DeliveryStop(stopID, items,origin,destination,truckType);
        }
    }

