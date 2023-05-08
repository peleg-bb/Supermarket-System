package Deliveries.DataAccessLayer;

import Deliveries.BusinessLayer.Enums_and_Interfaces.TruckType;
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

    public boolean addTruck(Truck truck) {
        String licensePlate = truck.getLicensePlate();
        int maxWeightTons = truck.getMaxWeightTons();
        TruckType truckType = truck.getType();
        String model = truck.getModel();
        String query = "INSERT INTO Trucks (license_plate, max_weight_tons, truck_type, model)" +
                " VALUES ('" + licensePlate + "', '" + maxWeightTons + "', '" + truckType+ "', '" + model + "');";
        try {
            conn.executeUpdate(query);
            return true;
        }
        catch (SQLException exception) {
            return false;
        }
    }

    private Truck getTruck(HashMap<String, Object> driverRecord) {
        throw new UnsupportedOperationException();
    }

}
