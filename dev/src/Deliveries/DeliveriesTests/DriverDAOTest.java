package Deliveries.DeliveriesTests;

import Deliveries.BusinessLayer.Driver;
import Deliveries.BusinessLayer.DriverController;
import Deliveries.BusinessLayer.Site;
import Deliveries.BusinessLayer.SiteGenerator;
import Deliveries.DataAccessLayer.DriverDAO;
import Deliveries.DataAccessLayer.SiteDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
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

    @Test
    void generateDriversToDB() {
        SiteGenerator siteGenerator = new SiteGenerator();
        List<Site> sites = siteGenerator.getSitesList();
        SiteDAO siteDAO = new SiteDAO();
        for (Site site : sites) {
           assertTrue(siteDAO.saveSite(site));
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