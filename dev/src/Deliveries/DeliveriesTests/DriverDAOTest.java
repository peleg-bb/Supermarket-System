package Deliveries.DeliveriesTests;

import Deliveries.BusinessLayer.*;
import Deliveries.BusinessLayer.Enums_and_Interfaces.DeliveryException;
import Deliveries.BusinessLayer.Enums_and_Interfaces.TruckType;
import Deliveries.DataAccessLayer.DriverDAO;
import HR.BusinessLayer.ShiftController;
import HR_Deliveries_Interface.HRIntegrator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DriverDAOTest {
    DriverDAO driverDAO;

    @Test
    void loadData() {
        Set<Driver> drivers = new HashSet<>();
        drivers = driverDAO.loadData();
        for (Driver driver : drivers) {
            System.out.println(driver);
        }
    }

    @Test
    void pickDriverIntegratedSuccess() {
        DriverController driverController = DriverController.getInstance();
        Timestamp timestamp = new Timestamp(2023-1900, 6, 4, 10, 0, 0, 0);
        Timestamp timestamp2 = new Timestamp(2023-1900, 6, 4, 12, 0, 0, 0);
        Truck truck = mock(Truck.class);

        try {
           driverController.pickDriver(truck, timestamp, timestamp2);
        } catch (DeliveryException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void checkAvailability() {
        HRIntegrator shiftController = ShiftController.getInstance();
        Timestamp timestamp = new Timestamp(2023-1900, 6, 4, 16, 0, 0, 0);
        assertTrue(shiftController.checkStoreAvailability("Tel Aviv", timestamp));
    }

    @Test
    void assignDriver() {
        DriverController driverController = DriverController.getInstance();
        Timestamp timestamp = new Timestamp(2023-1900, 6, 7, 16, 0, 0, 0);
        Timestamp timestamp2 = new Timestamp(2023-1900, 6, 7, 18, 0, 0, 0);
        Truck truck = mock(Truck.class);
        when(truck.getType()).thenReturn(TruckType.Regular);
        when(truck.getMaxWeightTons()).thenReturn( 1);
        String driverId;
        try {
            driverId = (driverController.pickDriver(truck, timestamp, timestamp2)).getId();
        } catch (DeliveryException e) {
            throw new RuntimeException(e);
        }
        HRIntegrator shiftController = ShiftController.getInstance();
        assertTrue(shiftController.assignDrivers(driverId, timestamp, timestamp2));

    }

    @BeforeEach
    void setUp() {
        driverDAO = new DriverDAO();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testLoadData() {
    }
}