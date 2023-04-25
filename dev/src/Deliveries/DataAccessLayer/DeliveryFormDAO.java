package Deliveries.DataAccessLayer;

import Deliveries.BusinessLayer.DeliveryForm;
import HR.DataAccessLayer.Connect;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class DeliveryFormDAO {
    private final Connect conn;
    public DeliveryFormDAO() {
        conn = Connect.getInstance();
    }

    public Set<DeliveryForm> loadData(){
        Set<DeliveryForm> deliveryForms = new HashSet<>();
        try {
            List<HashMap<String, Object>> deliveryFormDetails =
                    conn.executeQuery("SELECT * FROM DeliveryForms" +
                    " JOIN DeliveryStops ON DeliveryForms.form_id = DeliveryStops.form_id");
            for (HashMap<String, Object> deliveryFormRecord: deliveryFormDetails) {
                DeliveryForm deliveryForm = getDeliveryForm(deliveryFormRecord);
                deliveryForms.add(deliveryForm);
            }
            return deliveryForms;
        }
        catch (SQLException exception) {
            return null;
        }
    }

    private DeliveryForm getDeliveryForm(HashMap<String, Object> deliveryFormRecord) {
        int id = (Integer) deliveryFormRecord.get("form_id");
        String driverId = (String) deliveryFormRecord.get("driver_id");
        String truckId = (String) deliveryFormRecord.get("truck_id");
        Timestamp dispatchTime = (Timestamp) deliveryFormRecord.get("dispatch_time");
        Timestamp terminationTime = (Timestamp) deliveryFormRecord.get("termination_time");
        String status = (String) deliveryFormRecord.get("status");
        throw new UnsupportedOperationException();
    }
}
