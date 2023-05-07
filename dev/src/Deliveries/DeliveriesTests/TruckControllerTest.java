package Deliveries.DeliveriesTests;

import Deliveries.BusinessLayer.Enums_and_Interfaces.DeliveryException;
import Deliveries.BusinessLayer.Enums_and_Interfaces.TruckType;
import Deliveries.BusinessLayer.Truck;
import Deliveries.BusinessLayer.TruckController;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TruckControllerTest {
    TruckController truckController;


    @BeforeEach
    void setUp() {
        truckController = TruckController.getInstance();
    }

    @Test  @Order(1)
    void pickTruckSuccess() {
        try {
            Truck truck = truckController.pickTruck(TruckType.Regular, 10);
            assertEquals(truck.getType(), TruckType.Regular);
            assertTrue(truck.getMaxWeightTons() >= 10);
        } catch (DeliveryException e) {
            throw new RuntimeException(e);
        }
    }

    @Test  @Order(1)
    void pickRefrigeratedTruckSuccess() {
        try {
            Truck truck = truckController.pickTruck(TruckType.Refrigerated, 10);
            assertEquals(truck.getType(), TruckType.Refrigerated);
            assertTrue(truck.getMaxWeightTons() >= 10);
        } catch (DeliveryException e) {
            throw new RuntimeException(e);
        }
    }

    @Test @Order(2)
    void pickTruckWeightFail() {
        assertThrows(DeliveryException.class, () -> truckController.pickTruck(TruckType.Regular, 100));
    }
    @Test @Order(10)
    void pickTruckAvailabilityFail() {
        assertThrows(DeliveryException.class, this::pickAllTrucksLoop);
    }

    private void pickAllTrucksLoop() throws DeliveryException {
        for (int i = 0; i < truckController.getTruckFleetSize() + 1; i++) {
            truckController.pickTruck(TruckType.Regular, 1);
        }
    }

    @Test @Order(3)
    void testPickTruckSuccess() {
        try {
            Truck truck = truckController.pickTruck(TruckType.Refrigerated);
            assertEquals(truck.getType(), TruckType.Refrigerated);
        } catch (DeliveryException e) {
            throw new RuntimeException(e);
        }
    }

    @Test @Order(3)
    void PickRegularTruckSuccess() {
        try {
            Truck truck = truckController.pickTruck(TruckType.Regular);
            assertEquals(truck.getType(), TruckType.Regular);
        } catch (DeliveryException e) {
            throw new RuntimeException(e);
        }
    }
}