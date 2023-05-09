package Deliveries.DataAccessLayer;

import Deliveries.BusinessLayer.DeliveryForm;
import HR.DataAccessLayer.Connect;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeliveryFormDAO {
    private final Connect conn;

    //TODO: delete delivery form from db
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
        //TODO: return new DeliveryForm()
        throw new UnsupportedOperationException();
    }

    public boolean addDeliveryForm(DeliveryForm deliveryForm) {
        int formID = deliveryForm.getFormId();
        String driverID = deliveryForm.getDriver().getId();
        String truckLicensePlate = deliveryForm.getTruck().getLicensePlate();
        String dispatchTime = deliveryForm.getDispatchTime().toString();
        String terminationTime = deliveryForm.getEstimatedTerminationTime().toString();
        //TODO: String status = deliveryForm.פ
        String query = "INSERT INTO DeliveryForms (form_id, driver_id, truck_license_plate, dispatch_time, termination_time)" +
                " VALUES ('" + formID + "', '" + driverID + "', '" + truckLicensePlate+ "', '" + dispatchTime + "', '" + terminationTime + "');";
        try {
            conn.executeUpdate(query);
            return true;
        }
        catch (SQLException exception) {
            return false;
        }
    }
}
