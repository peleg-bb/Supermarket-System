package Deliveries.BusinessLayer;

import Deliveries.BusinessLayer.Enums_and_Interfaces.TruckType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class License {
    private final int weightAllowedTons;
    private final Set<TruckType> truckTypesAllowed;

    public License() {
        this.weightAllowedTons = 15;
        this.truckTypesAllowed = new HashSet<>();
        this.truckTypesAllowed.add(TruckType.Regular);
        this.truckTypesAllowed.add(TruckType.Refrigerated);
    }

    public License(int weightAllowed, int regularAllowed, int refrigeratedAllowed) {
        this.weightAllowedTons = weightAllowed;
        this.truckTypesAllowed = new HashSet<>();
        if (regularAllowed == 1) {
            this.truckTypesAllowed.add(TruckType.Regular);
        }
        if (refrigeratedAllowed == 1) {
            this.truckTypesAllowed.add(TruckType.Refrigerated);
        }
    }

    public int getWeightAllowedTons() {
        return weightAllowedTons;
    }

    public Set<TruckType> getTruckTypesAllowed() {
        return truckTypesAllowed;
    }

    public boolean isLicensed(Truck truck) {
        int weight = truck.getMaxWeightTons();
        TruckType type = truck.getType();
        return weight <= weightAllowedTons && truckTypesAllowed.contains(type);
    }
}
