package Deliveries.DataAccessLayer;

import Deliveries.BusinessLayer.DeliveryStop;
import Deliveries.BusinessLayer.Enums_and_Interfaces.TruckType;
import Deliveries.BusinessLayer.Site;
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


    public boolean addStop(DeliveryStop stop) {
        int stopID = stop.getShipmentInstanceID();
        String origin = stop.getOrigin().getName();
        String destination = stop.getDestination().getName();
        String truckType = stop.getTruckTypeRequired().toString();
        String status = stop.getStatus().toString();
        //String formId = "-1";
        int formID = stop.getFormID();
        String query = "INSERT INTO DeliveryStops (stop_id, origin_name, destination_name, truck_type, status, form_id)" +
                " VALUES ('" + stopID + "', '" + origin + "', '" + destination+ "', '" + truckType + "', '" + status + "', '" + formID + "');";
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
            HashMap<String, Integer> items = new HashMap<>();
            Site originSite = getSiteByName(origin);
            Site destinationSite = getSiteByName(destination);
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

