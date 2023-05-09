package Deliveries.DataAccessLayer;

import Deliveries.BusinessLayer.Driver;
import Deliveries.BusinessLayer.Site;
import HR.DataAccessLayer.Connect;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;


public class SiteDAO {
    private final Connect conn;

    public SiteDAO() {
        conn = Connect.getInstance();
    }

    //TODO: insert drivers to db
    public Set<Driver> loadData() {
//        Set<Driver> drivers = new HashSet<>();
//        try {
//            List<HashMap<String, Object>> driverDetails = conn.executeQuery("SELECT * FROM Drivers JOIN " +
//                    "DriverLicenses ON Drivers.driver_id = DriverLicenses.driver_id");
//            for (HashMap<String, Object> driverRecord: driverDetails) {
//                Driver driver = getDriver(driverRecord);
//                drivers.add(driver);
//            }
//            return drivers;
//        }
//        catch (SQLException exception) {
//            return null;
//        }
        throw new UnsupportedOperationException();
    }


    private Driver getDriver(HashMap<String, Object> driverDetails) {
//        String id = ((Integer) driverDetails.get("driver_id")).toString();
//        String name = (String) driverDetails.get("driver_name");
//        String phone = (String) driverDetails.get("phone");
//
//        int weightAllowed = Integer.parseInt(driverDetails.get("weight_allowed_tons").toString());
//        Integer regularAllowed = (Integer) driverDetails.get("regular_allowed");
//        Integer refrigeratedAllowed = (Integer) driverDetails.get("refrigerated_allowed");
//        License license = new License(weightAllowed, regularAllowed, refrigeratedAllowed);
//        return new Driver(id, name, phone, license);

        throw new UnsupportedOperationException();
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
