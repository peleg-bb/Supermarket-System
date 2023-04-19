package Deliveries;



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
}
