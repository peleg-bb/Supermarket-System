package Deliveries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SiteGenerator {
    private static final String[] NAMES = {"Site A", "Site B", "Site C", "Site D", "Site E", "Site F", "Site G", "Site H", "Site I", "Site J"};
    private static final String[] ZONES = {"Zone 1", "Zone 2", "Zone 3", "Zone 4"};
    public List<Site> sitesList;
    public SiteGenerator(){
        sitesList = generateSites();
    }

    private List<Site> generateSites() {
        List<Site> sites = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            String name = NAMES[i];
            String address = "Address " + (i+1);
            String contactName = "Contact " + (i+1);
            String contactPhone = "555-1234";
            String deliveryZone = ZONES[rand.nextInt(ZONES.length)];
            Site site = new Site(name, address, contactName, contactPhone, deliveryZone);
            sites.add(site);
        }
        return sites;
    }

    public List<Site> getSitesList() {
        return sitesList;
    }
}
