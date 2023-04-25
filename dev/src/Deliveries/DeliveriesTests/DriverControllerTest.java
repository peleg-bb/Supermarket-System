package Deliveries.DeliveriesTests;

import Deliveries.BusinessLayer.Driver;
import Deliveries.BusinessLayer.DriverController;
import Deliveries.BusinessLayer.Enums_and_Interfaces.TruckType;
import Deliveries.BusinessLayer.Truck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DriverControllerTest {
    private DriverController driverController;
    @Mock
    Truck truck1;
    @Mock
    Truck truck2;
    @Mock
    Truck truck3;


    @BeforeEach
    void setUp() {
        driverController = DriverController.getInstance();
        driverController.generateFleet(20);

    }

    @Test
    void pickDriver() {
        when(truck1.getType()).thenReturn(TruckType.Regular);
        when(truck2.getType()).thenReturn(TruckType.Refrigerated);
        
    }
}