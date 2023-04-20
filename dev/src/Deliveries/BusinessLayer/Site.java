package Deliveries.BusinessLayer;

public class Site {
    private String name;
    private String address;
    private String contactName;
    private String contactPhone;
    private String deliveryZone;

    public Site(String name, String address, String contactName, String contactPhone, String deliveryZone) {
        this.name = name;
        this.address = address;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.deliveryZone = deliveryZone;
    }

    @Override
    public String toString() {
        return "Site{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getDeliveryZone() {
        return deliveryZone;
    }
}
