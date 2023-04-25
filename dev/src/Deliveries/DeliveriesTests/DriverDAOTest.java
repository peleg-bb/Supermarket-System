package Deliveries.DeliveriesTests;

import Deliveries.BusinessLayer.Driver;
import Deliveries.DataAccessLayer.DriverDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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