package Deliveries.DataAccessLayer;

import Deliveries.BusinessLayer.Site;
import HR.DataAccessLayer.Connect;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SiteDAO {
    private final Connect conn;

    public SiteDAO() {
        conn = Connect.getInstance();
    }

    //TODO: insert drivers to db
    public Set<Site> loadData() {
        Set<Site> sites = new HashSet<>();
        try {
            List<HashMap<String, Object>> sitesDetails = conn.executeQuery("SELECT * FROM Sites");
            for (HashMap<String, Object> siteRecord: sitesDetails) {
                Site site = getSite(siteRecord);
                sites.add(site);
            }
            return sites;
        }
        catch (SQLException exception) {
            return null;
        }
    }


    public static Site getSite(HashMap<String, Object> siteDetails) {
        String name = (String) siteDetails.get("name");
        String address = (String) siteDetails.get("address");
        String contactName = (String) siteDetails.get("contact_name");
        String contactPhone = (String) siteDetails.get("contact_phone");
        int deliveryZone = (Integer) siteDetails.get("delivery_zone");

        return new Site(name, address, contactName, contactPhone,deliveryZone);
    }



    public boolean saveSite(Site site) {
        String name = site.getName();
        String address = site.getAddress();
        String contactName = site.getContactName();
        String phone = site.getContactPhone();
        String deliveryZone = ((Integer) site.getDeliveryZone()).toString();
        String query = "INSERT INTO Sites (name, address, contact_name, contact_phone, delivery_zone) VALUES ('" +
                name + "', '" + address + "', '" + contactName + "', '" + phone + "', '" + deliveryZone + "')";
        try {
            conn.executeUpdate(query);
            return true;
        }
        catch (SQLException exception) {
            return false;
        }
    }
}
