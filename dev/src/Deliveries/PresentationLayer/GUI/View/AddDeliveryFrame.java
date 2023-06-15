package Deliveries.PresentationLayer.GUI.View;

import Deliveries.BusinessLayer.DeliveryForm;
import Deliveries.BusinessLayer.Generators.SiteGenerator;
import Deliveries.BusinessLayer.Site;
import Deliveries.PresentationLayer.GUI.Model.AddDeliveryModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;

public class AddDeliveryFrame extends AbstractFrame{
    public AddDeliveryFrame(Set<DeliveryForm> deliveryForms) {
        super(deliveryForms.size() + 1, new AddDeliveryModel());
        addButton("Return to main menu");
        setLayout(new BorderLayout());

        // Create the main panel and set its layout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create the label for "Pick an origin"
        JLabel label = new JLabel("Pick an origin");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        // Add the label to the main panel
        mainPanel.add(label, BorderLayout.NORTH);
        buttonsPanel.add(Box.createVerticalStrut(20), BorderLayout.CENTER);

        SiteGenerator siteGenerator = new SiteGenerator();
        List<Site> sitesList = siteGenerator.getSitesList();
        for (Site site : sitesList) {
            addButton(sitesList.indexOf(site) + "- " + site);
        }
        add(mainPanel, BorderLayout.CENTER);

    }
}
