package Deliveries.DeliveriesTests;

import Deliveries.BusinessLayer.*;
import Deliveries.BusinessLayer.Enums_and_Interfaces.TruckType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeliveryFormsControllerTest {
    DeliveryFormsController deliveryFormsController;
    @Mock
    DeliveryStop deliveryStop1;
    @Mock
    DeliveryStop deliveryStop2;
    @Mock
    DeliveryForm deliveryForm1;
    @Mock
    TruckController truckController;
    @Mock
    Truck truck1;
    @Mock
    DriverController driverController;

    @BeforeEach
    void setUp() {
        truckController = TruckController.getInstance();
        driverController = DriverController.getInstance();
        deliveryFormsController = DeliveryFormsController.getInstance();
        deliveryStop1 = mock(DeliveryStop.class);
        deliveryStop2 = mock(DeliveryStop.class);
        deliveryForm1 = mock(DeliveryForm.class);
        deliveryFormsController.setTestingMode();
    }

    @Test
    void removeDeliveryForm() throws Exception {
        // TODO: implement with mocks
        Site site1 = mock(Site.class);
        deliveryFormsController.createForm(new ArrayList<>(), site1);
        assertEquals(1, deliveryFormsController.getPendingDeliveryForms().size());
        deliveryFormsController.removeDeliveryForm(deliveryForm1);
        assertEquals(0, deliveryFormsController.getPendingDeliveryForms().size());
        ;
    }


    @Test
    void printCompletedDeliveryForms() {
    }

    @Test
    void terminateDeliveryForm() {
    }

    @Test
    void createForm() {
    }

    @Test
    void getTruckTypeRegularSuccess() throws Exception {
        when(deliveryStop1.getTruckTypeRequired()).thenReturn(TruckType.Regular);
        when(deliveryStop2.getTruckTypeRequired()).thenReturn(TruckType.Regular);
        List<DeliveryStop> deliveryStops = List.of(deliveryStop1, deliveryStop2);

        assertEquals(TruckType.Regular, deliveryFormsController.getTruckTypeTest(deliveryStops));
    }

    @Test
    void getTruckTypeRegularFail() {
        when(deliveryStop1.getTruckTypeRequired()).thenReturn(TruckType.Regular);
        when(deliveryStop2.getTruckTypeRequired()).thenReturn(TruckType.Refrigerated);
        List<DeliveryStop> deliveryStops = List.of(deliveryStop1, deliveryStop2);

        try{assertNotEquals(TruckType.Regular, deliveryFormsController.getTruckTypeTest(deliveryStops));}
        catch (Exception ignored) {}

    }

    @Test
    void getTruckTypeRefrigeratedSuccess() {
        when(deliveryStop1.getTruckTypeRequired()).thenReturn(TruckType.Regular);
        when(deliveryStop2.getTruckTypeRequired()).thenReturn(TruckType.Refrigerated);
        List<DeliveryStop> deliveryStops = List.of(deliveryStop1, deliveryStop2);

        try{assertEquals(TruckType.Refrigerated, deliveryFormsController.getTruckTypeTest(deliveryStops));}
        catch (Exception ignored) {}
    }

    @Test
    void getTruckTypeRefrigeratedFail() {
        when(deliveryStop1.getTruckTypeRequired()).thenReturn(TruckType.Regular);
        when(deliveryStop2.getTruckTypeRequired()).thenReturn(TruckType.Regular);
        List<DeliveryStop> deliveryStops = List.of(deliveryStop1, deliveryStop2);

        try{assertNotEquals(TruckType.Refrigerated, deliveryFormsController.getTruckTypeTest(deliveryStops));}
        catch (Exception ignored) {}

    }
}