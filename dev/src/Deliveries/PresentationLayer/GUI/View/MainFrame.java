package Deliveries.PresentationLayer.GUI.View;

import Deliveries.BusinessLayer.Generators.SiteGenerator;
import Deliveries.BusinessLayer.Site;
import Deliveries.PresentationLayer.GUI.Model.MainMenuModel;

import java.util.List;

public class MainFrame extends AbstractFrame {
    public MainFrame() {
        super(4, new MainMenuModel());
        SiteGenerator siteGenerator = new SiteGenerator();
        List<Site> sitesList = siteGenerator.getSitesList(); // Not sure if needed
        addBottom(e -> {}, "Add a delivery stop");
        addBottom(e -> {}, "Remove a delivery stop");
        addBottom(e -> {}, "Execute a delivery");
        addBottom(e -> {}, "Exit");
  }

}