package Deliveries.DeliveriesTests;

import Deliveries.BusinessLayer.*;
import Deliveries.BusinessLayer.Enums_and_Interfaces.DeliveryException;
import Deliveries.BusinessLayer.Enums_and_Interfaces.DeliveryStatus;
import Deliveries.BusinessLayer.Enums_and_Interfaces.TruckType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryFormTest {

    private DeliveryForm deliveryForm;
    private DeliveryFormsController deliveryFormsController;
    private Site origin;
    private Site destination2;
    private Site destination;
    private TruckController truckController;
    private DriverController driverController;
    private DeliveryStop stop1;
    private DeliveryStop stop2;
    private Truck truck;
    private Driver driver;

    @BeforeEach
    void setUp() {
        SiteGenerator siteGenerator = new SiteGenerator();
        List<Site> siteList = siteGenerator.getSitesList();
        origin = siteList.get(0);
        destination2 = siteList.get(1);
        destination = siteList.get(2);

        Map<String, Integer> items1 = new HashMap<>();
        items1.put("Box", 5);
        items1.put("Envelope", 10);
        stop1 = new DeliveryStop(1, items1, origin, destination, TruckType.Regular);
        Map<String, Integer> items2 = new HashMap<>();
        items2.put("Pallet", 2);
        items2.put("Crate", 1);
        stop2 = new DeliveryStop(2, items2, origin, destination2, TruckType.Refrigerated);
        driverController = DriverController.getInstance();
        truckController = TruckController.getInstance();
        try {
            truck = truckController.pickTruck(TruckType.Regular);
            driver = driverController.pickDriver(truck.getType(), truck.getMaxWeightTons());
        } catch (DeliveryException e) {
            e.printStackTrace();
        }

        List<DeliveryStop> destinations = new ArrayList<>();
        destinations.add(stop1);
        destinations.add(stop2);
        deliveryForm = new DeliveryForm(1, new ArrayList<>(), origin,
                truck.getMaxWeightTons(), driver, truck);
        deliveryForm.setWeightMeasurer(new MockWeightMeasurer());
        deliveryFormsController = DeliveryFormsController.getInstance();
        deliveryFormsController.addDeliveryForm(deliveryForm);
    }

    @Test
    void addDeliveryStop() {
        deliveryForm.addDeliveryStop(stop1);
        assertEquals(1, deliveryForm.getDestinationSitesToVisit().size());
        assertEquals(stop1, deliveryForm.getDestinationSitesToVisit().get(0));
    }

    @Test
    void visitDeliveryStop() {
        deliveryForm.addDeliveryStop(stop1);
        deliveryForm.addDeliveryStop(stop2);
        deliveryForm.visitDeliveryStop(stop1);
        assertEquals(DeliveryStatus.DELIVERED, stop1.getStatus());
        assertEquals(DeliveryStatus.NOT_STARTED, stop2.getStatus());
    }

    @Test
    void startJourney() {
        deliveryForm.addDeliveryStop(stop1);
        deliveryForm.addDeliveryStop(stop2);
        assertEquals(DeliveryStatus.NOT_STARTED, stop1.getStatus());
        assertEquals(DeliveryStatus.NOT_STARTED, stop2.getStatus());
        deliveryForm.startJourney();
        assertEquals(DeliveryStatus.DELIVERED, stop1.getStatus());
        assertEquals(DeliveryStatus.DELIVERED, stop2.getStatus());
    }

    @Test
    void cancelForm() {
        deliveryForm.addDeliveryStop(stop1);
        deliveryForm.addDeliveryStop(stop2);
        deliveryForm.cancelForm();
        assertEquals(DeliveryStatus.CANCELLED, stop1.getStatus());
        assertEquals(DeliveryStatus.CANCELLED, stop2.getStatus());
        assertEquals(new ArrayList<DeliveryStop>(), deliveryForm.getDestinationSitesVisited());
    }

    @Test
    void cancelStop() {
        deliveryForm.addDeliveryStop(stop1);
        deliveryForm.addDeliveryStop(stop2);
        deliveryForm.cancelStop(stop1);
        deliveryForm.startJourney();
        assertEquals(DeliveryStatus.CANCELLED, stop1.getStatus());
        assertEquals(DeliveryStatus.DELIVERED, stop2.getStatus());
    }

    @Test
    void setDispatchWeightTons() {
        int dispatchWeightTons = deliveryForm.getMaxWeightAllowed();
        deliveryForm.setDispatchWeightTons(dispatchWeightTons);
        assertEquals(dispatchWeightTons, deliveryForm.getDispatchWeightTons());
    }

    @Test
    void setDestinationSitesToVisit() {
        List<DeliveryStop> destinationSites = new ArrayList<>();
        deliveryForm.setDestinationSitesToVisit(destinationSites);
        assertEquals(destinationSites, deliveryForm.getDestinationSitesToVisit());


        destinationSites.add(stop1);
        destinationSites.add(stop2);
        deliveryForm.setDestinationSitesToVisit(destinationSites);
        assertEquals(destinationSites, deliveryForm.getDestinationSitesToVisit());
    }

    @Test
    void getFormId() {
        assertEquals(1, deliveryForm.getFormId());
    }

    @Test
    void getTruckType() {
        TruckType truckType = TruckType.Regular;
        assertEquals(truckType, deliveryForm.getTruckType());
    }

    @Test
    void getDispatchWeightTons() {
        int dispatchWeightTons = 5;
        deliveryForm.setDispatchWeightTons(dispatchWeightTons);
        assertEquals(dispatchWeightTons, deliveryForm.getDispatchWeightTons());
    }
}

