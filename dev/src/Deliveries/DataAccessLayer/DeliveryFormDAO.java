package Deliveries.DataAccessLayer;

import Deliveries.BusinessLayer.DeliveryForm;
import Deliveries.BusinessLayer.DeliveryStop;
import Deliveries.BusinessLayer.Enums_and_Interfaces.DeliveryException;
import Deliveries.BusinessLayer.Enums_and_Interfaces.DeliveryStatus;
import Deliveries.BusinessLayer.Enums_and_Interfaces.TruckType;
import Deliveries.BusinessLayer.Site;
import HR.DataAccessLayer.Connect;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import static Deliveries.BusinessLayer.Enums_and_Interfaces.DeliveryStatus.DELIVERED;
import static Deliveries.BusinessLayer.Enums_and_Interfaces.DeliveryStatus.NOT_STARTED;

public class DeliveryFormDAO {
    private final Connect conn;


    public DeliveryFormDAO() {
        conn = Connect.getInstance();
    }

    public Set<DeliveryForm> loadData() {
        Set<DeliveryForm> deliveryForms = new HashSet<>();
        try {
            List<HashMap<String, Object>> deliveryFormDetails =
                    conn.executeQuery("SELECT * FROM DeliveryForms" );
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
        String ID = (String) deliveryFormRecord.get("form_id");
        int id = Integer.parseInt(ID);
//        String driverId = (String) deliveryFormRecord.get("driver_id");
//        String truckId = (String) deliveryFormRecord.get("truck_id");
        Timestamp dispatchTime = Timestamp.valueOf((String) deliveryFormRecord.get("dispatch_time"));
//        Timestamp terminationTime = (Timestamp) deliveryFormRecord.get("termination_time"); // unnecessary
        String formStatus = (String) deliveryFormRecord.get("status");
        DeliveryStatus status;
        if (formStatus.equals("completed")) {
            status = DELIVERED;
        }
        else { // formStatus.equals("pending")
            status = NOT_STARTED;
        }
        // gets the list of delivery stops on the form
        String origin = (String) deliveryFormRecord.get("origin");
        Site originSite = getSiteByName(origin);
        List<DeliveryStop> visitedDestinations = new ArrayList<>();
        List<DeliveryStop> unvisitedDestinations = new ArrayList<>();
        try {
            List<HashMap<String, Object>> deliveryStopDetails = conn.executeQuery("SELECT * FROM DeliveryStops" +
                    " Where form_id =" + id);
            for (HashMap<String, Object> deliveryStopRecord : deliveryStopDetails) {
                int stopID = (Integer) deliveryStopRecord.get("stop_id");
                String originName = (String) deliveryStopRecord.get("origin_name");
                String destination = (String) deliveryStopRecord.get("destination_name");
                String truckTypeString = (String) deliveryStopRecord.get("truck_type");
                TruckType truckType = TruckType.valueOf(truckTypeString.toUpperCase());
                HashMap<String, Integer> items = new HashMap<>();
                Site originS = getSiteByName(originName);
                Site destinationSite = getSiteByName(destination);
                DeliveryStop deliveryStop = new DeliveryStop(stopID, items, originS, destinationSite, truckType);
                String stopStatus = (String) deliveryStopRecord.get("status");
                if (stopStatus.equals("DELIVERED")) {
                    visitedDestinations.add(deliveryStop);
                    deliveryStop.setStatus(DELIVERED);
                }
                else {
                    unvisitedDestinations.add(deliveryStop);
                }
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }

        DeliveryForm deliveryForm;
        try {
            deliveryForm = new DeliveryForm(id, unvisitedDestinations, visitedDestinations, originSite,
                    dispatchTime, status);
        } catch (DeliveryException e) {
            throw new RuntimeException(e);
        }
        return deliveryForm;
    }


    public boolean addDeliveryForm(DeliveryForm deliveryForm) {
        int formID = deliveryForm.getFormId();
        String driverID = deliveryForm.getDriver().getId();
        String truckLicensePlate = deliveryForm.getTruck().getLicensePlate();
        String dispatchTime = deliveryForm.getDispatchTime().toString();
        String terminationTime = deliveryForm.getEstimatedTerminationTime().toString();
        String status = deliveryForm.getDestinationSitesToVisit().isEmpty() ? "completed" : "pending";
        String origin = deliveryForm.getOriginSite().getName();
        String query = "INSERT INTO DeliveryForms (form_id, driver_id, truck_license_plate, dispatch_time, termination_time, status, origin)" +
                " VALUES ('" + formID + "', '" + driverID + "', '" + truckLicensePlate + "', '" + dispatchTime + "', " +
                "'" + terminationTime + "', '" + status + "', '" + origin + "');";
        // Note - origin here breaks 3NF, could do without it but meh (it breaks 3NF in the delivery stop table to be precise)
        try {
            conn.executeUpdate(query);
            return true;
        } catch (SQLException exception) {
            return false;
        }
    }

    public Site getSiteByName(String name) {
        try {
            List<HashMap<String, Object>> siteDetails = conn.executeQuery("SELECT * FROM Sites Where name = '" + name + "'");
            return SiteDAO.getSite(siteDetails.get(0));
        } catch (SQLException exception) {
            return null;
        }
    }

    public void setStatus(int formId, DeliveryStatus deliveryStatus) {
        String status = deliveryStatus == DELIVERED ? "completed" : "pending";
        String query = "UPDATE DeliveryForms SET status = '" + status + "' WHERE form_id = " + formId;
        try {
            conn.executeUpdate(query);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
