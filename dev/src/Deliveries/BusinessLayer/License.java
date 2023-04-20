package Deliveries.BusinessLayer;

import Deliveries.BusinessLayer.Enums_and_Interfaces.TruckType;

import java.util.ArrayList;
import java.util.List;

public class License {
    private int weightAllowedTons;
    private List<TruckType> truckTypesAllowed;

    public License() {
        this.weightAllowedTons = 15;
        this.truckTypesAllowed = new ArrayList<>();
        this.truckTypesAllowed.add(TruckType.Regular);
        this.truckTypesAllowed.add(TruckType.Refrigerated);
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
