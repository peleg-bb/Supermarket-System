package Deliveries.BusinessLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SiteGenerator {
    private static final String[] NAMES = {"Site A", "Site B", "Site C", "Site D", "Site E", "Site F", "Site G", "Site H", "Site I", "Site J"};
    private static final int[] ZONES = {1, 2, 3, 4, 5};
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
            int deliveryZone = ZONES[rand.nextInt(ZONES.length)];
            Site site = new Site(name, address, contactName, contactPhone, deliveryZone);
            sites.add(site);
        }
        return sites;
    }

    public List<Site> getSitesList() {
        return sitesList;
    }
}
