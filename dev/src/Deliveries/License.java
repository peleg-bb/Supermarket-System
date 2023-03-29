package Deliveries;

import java.util.ArrayList;
import java.util.List;

public class License {
    private int weightAllowedTons;
    private List<TruckType> truckTypesAllowed;

    public License() {
        this.weightAllowedTons = 4;
        this.truckTypesAllowed = new ArrayList<TruckType>();
        this.truckTypesAllowed.add(TruckType.Regular);
    }

    public License(int weightAllowed, List<TruckType> truckTypesAllowed) {
        this.weightAllowedTons = weightAllowed;
        this.truckTypesAllowed = truckTypesAllowed;
    }

    public int getWeightAllowedTons() {
        return weightAllowedTons;
    }

    public List<TruckType> getTruckTypesAllowed() {
        return truckTypesAllowed;
    }
}
