package Deliveries.BusinessLayer;


import Deliveries.BusinessLayer.Enums_and_Interfaces.Availability;
import Deliveries.BusinessLayer.Enums_and_Interfaces.TruckType;

public class Driver {
    private final String name;
    private final String id;
    private final String phone;

    @Override
    public String toString() {
        return "Driver{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    private License license;

    private Availability availability;

    public Driver(String name, String id, String phone) {
        this.name = name;
        this.id = id;
        this.phone = phone;
        this.license = new License();
        availability = Availability.Available;
        // TODO: implement license
    }

    public String getName() {
        return name;
    }

    public License getLicense() {
        return license;
    }

    public Availability getAvailability() {
        return availability;
    }

    public String getId() {
        return id;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public void freeDriver() {
        availability = Availability.Available;
    }

    public boolean isLicensed(Truck truck) {
        return license.isLicensed(truck);
    }
}
