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

    public Site getSiteByName(String name){
        try {
            List<HashMap<String, Object>> siteDetails = conn.executeQuery("SELECT * FROM Sites Where name = " + name);
            Site site = SiteDAO.getSite(siteDetails.get(0));
            return site;
        }
        catch (SQLException exception) {
            return null;
        }
    }

        private DeliveryStop getStop(HashMap<String, Object> deliveryStopRecord) {
            int stopID = (Integer) deliveryStopRecord.get("stop_id");
            String origin = (String) deliveryStopRecord.get("origin_name");
            String destination = (String) deliveryStopRecord.get("destination_name");
            TruckType truckType = (TruckType) deliveryStopRecord.get("truck_type");
            HashMap<String, Integer> items = new HashMap<>();
            Site originSite = getSiteByName(origin);
            Site destinationSite = getSiteByName(destination);
            return new DeliveryStop(stopID, items,originSite,destinationSite,truckType);
        }
    }

