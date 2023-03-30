package Deliveries;



public class Driver {
    private final String name;
    private final String id;
    private final String phone;
    private License license;

    private DriversAvailability availability;

    public Driver(String name, String id, String phone) {
        this.name = name;
        this.id = id;
        this.phone = phone;
        this.license = new License();
        availability = DriversAvailability.Available;
    }



    public String getName() {
        return name;
    }

    public License getLicense() {
        return license;
    }


}
