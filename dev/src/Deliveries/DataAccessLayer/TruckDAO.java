package Deliveries.DataAccessLayer;

import Deliveries.BusinessLayer.Truck;
import HR.DataAccessLayer.Connect;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TruckDAO {
    private final Connect conn;

    //TODO: insert trucks to db
    public TruckDAO() {
        conn = Connect.getInstance();
    }

    public Set<Truck> loadData(){
        Set<Truck> trucks = new HashSet<>();
        try {
            List<HashMap<String, Object>> truckDetails = conn.executeQuery("SELECT * FROM Trucks");
            for (HashMap<String, Object> truckRecord: truckDetails) {
                Truck truck = getTruck(truckRecord);
                trucks.add(truck);
            }
            return trucks;
        }
        catch (SQLException exception) {
            return null;
        }
    }

    private Truck getTruck(HashMap<String, Object> driverRecord) {
        throw new UnsupportedOperationException();
    }

}
