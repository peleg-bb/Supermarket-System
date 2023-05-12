package Deliveries.DataAccessLayer;

import Deliveries.BusinessLayer.DeliveryForm;
import Deliveries.BusinessLayer.DeliveryStop;
import Deliveries.BusinessLayer.Site;
import HR.DataAccessLayer.Connect;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class DeliveryFormDAO {
    private final Connect conn;


    public DeliveryFormDAO() {
        conn = Connect.getInstance();
    }

    public Set<DeliveryForm> loadData() {
        Set<DeliveryForm> deliveryForms = new HashSet<>();
        try {
            List<HashMap<String, Object>> deliveryFormDetails =
                    conn.executeQuery("SELECT * FROM DeliveryForms" +
                            " JOIN DeliveryStops ON DeliveryForms.form_id = DeliveryStops.form_id");
            for (HashMap<String, Object> deliveryFormRecord : deliveryFormDetails) {
                DeliveryForm deliveryForm = getDeliveryForm(deliveryFormRecord);
                deliveryForms.add(deliveryForm);
            }
            return deliveryForms;
        } catch (SQLException exception) {
            return null;
        }

    }

    private DeliveryForm getDeliveryForm(HashMap<String, Object> deliveryFormRecord) {
        int id = (Integer) deliveryFormRecord.get("form_id");
//        String driverId = (String) deliveryFormRecord.get("driver_id");
//        String truckId = (String) deliveryFormRecord.get("truck_id");
        Timestamp dispatchTime = (Timestamp) deliveryFormRecord.get("dispatch_time");
//        Timestamp terminationTime = (Timestamp) deliveryFormRecord.get("termination_time");
//        String status = (String) deliveryFormRecord.get("status");

        // TODO: Add destinations
        String origin = (String) deliveryFormRecord.get("origin");
        Site originSite = getSiteByName(origin);
        List<DeliveryStop> destinations = new ArrayList<>();
        DeliveryStop
        DeliveryForm deliveryForm;
        try {
            deliveryForm = new DeliveryForm(id, destinations, originSite, dispatchTime);
        } catch (Exception e) {
            return null;
        }
        return deliveryForm;
    }

    public boolean addDeliveryForm(DeliveryForm deliveryForm) {
        int formID = deliveryForm.getFormId();
        String driverID = deliveryForm.getDriver().getId();
        String truckLicensePlate = deliveryForm.getTruck().getLicensePlate();
        String dispatchTime = deliveryForm.getDispatchTime().toString();
        String terminationTime = deliveryForm.getEstimatedTerminationTime().toString();
        String status = deliveryForm.getDestinationSitesToVisit().isEmpty() ? "Completed" : "PENDING";
        String origin = deliveryForm.getOriginSite().getName();
        String query = "INSERT INTO DeliveryForms (form_id, driver_id, truck_license_plate, dispatch_time, termination_time, status, origin)" +
                " VALUES ('" + formID + "', '" + driverID + "', '" + truckLicensePlate + "', '" + dispatchTime + "', '" + terminationTime + "', '" + status + "', '" + origin + "');";
        try {
            conn.executeUpdate(query);
            return true;
        } catch (SQLException exception) {
            return false;
        }
    }

    public Site getSiteByName(String name) {
        try {
            List<HashMap<String, Object>> siteDetails = conn.executeQuery("SELECT * FROM Sites Where name = " + name);
            Site site = SiteDAO.getSite(siteDetails.get(0));
            return site;
        } catch (SQLException exception) {
            return null;
        }
    }
}
