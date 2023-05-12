package Deliveries.DataAccessLayer;

import Deliveries.BusinessLayer.DeliveryStop;
import Deliveries.BusinessLayer.Enums_and_Interfaces.DeliveryStatus;
import Deliveries.BusinessLayer.Enums_and_Interfaces.TruckType;
import Deliveries.BusinessLayer.Site;
import HR.DataAccessLayer.Connect;

import java.sql.SQLException;
import java.util.*;

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


    public boolean addStop(DeliveryStop stop) {
        int stopID = stop.getShipmentInstanceID();
        String origin = stop.getOrigin().getName();
        String destination = stop.getDestination().getName();
        String truckType = stop.getTruckTypeRequired().toString();
        String status = stop.getStatus().toString();
//        int formID = stop.getFormID();
        //items
        Map<String, Integer> items = stop.getItems();
        String query1 = "INSERT INTO DeliveryStops (stop_id, origin_name, destination_name, truck_type, status)" +
                " VALUES ('" + stopID + "', '" + origin + "', '" + destination+ "', '" + truckType + "', '" + status + "');" ;
//        String query2 = "INSERT INTO Items (form_id, item_name, quantity)" +
//                " VALUES ('" + formID + "', '" + itemName + "', '" + quantity+  "');";
        try {
            conn.executeUpdate(query1);
           for(Map.Entry<String,Integer> item : items.entrySet()) {
               String itemName = item.getKey();
               int quantity = item.getValue();

               String query2 = "INSERT INTO Items (stop_id, item_name, quantity)" +
                       " VALUES ('" + stop.getShipmentInstanceID() + "', '" + itemName + "', '" + quantity + "');";
               conn.executeUpdate(query2);
           }

            return true;
        }
        catch (SQLException exception) {
            return false;
        }
    }

    public boolean updateStatus(int stopId, DeliveryStatus status) {
        String query = "UPDATE DeliveryStops SET status = '" + status.toString() + "' WHERE stop_id = '" + stopId + "';";
        try {
            conn.executeUpdate(query);
            return true;
        }
        catch (SQLException exception) {
            return false;
        }
    }

    public boolean updateFormID(int stopId, int formID) {
        String query = "UPDATE DeliveryStops SET form_id = '" + formID + "' WHERE stop_id = '" + stopId + "';";
        try {
            conn.executeUpdate(query);
            return true;
        }
        catch (SQLException exception) {
            return false;
        }
    }

    public DeliveryStop getStop(HashMap<String, Object> deliveryStopRecord) {
            int stopID = (Integer) deliveryStopRecord.get("stop_id");
            String origin = (String) deliveryStopRecord.get("origin_name");
            String destination = (String) deliveryStopRecord.get("destination_name");
            String truckTypeString = (String) deliveryStopRecord.get("truck_type");
            TruckType truckType = TruckType.valueOf(truckTypeString);

            Site originSite = getSiteByName(origin);
            Site destinationSite = getSiteByName(destination);
            HashMap<String, Integer> items = new HashMap<>();
        try {
            List<HashMap<String, Object>> itemDetails = conn.executeQuery("SELECT * FROM Items Where stop_id =" + stopID );
            for (HashMap<String, Object> itemRecord : itemDetails) {
                String itemName =  (String) itemRecord.get("item_name");
                int quantity = (Integer) itemRecord.get("origin_name");
                items.put(itemName,quantity);
            }
        }
        catch (Exception e){
            return null;
        }
            return new DeliveryStop(stopID, items,originSite,destinationSite,truckType);
        }

    public Site getSiteByName(String siteName){
        try {
            List<HashMap<String, Object>> siteDetails = conn.executeQuery("SELECT * FROM Sites Where name = '" + siteName + "';" );
            return SiteDAO.getSite(siteDetails.get(0));
        }
        catch (SQLException exception) {
            return null;
        }
    }



}

