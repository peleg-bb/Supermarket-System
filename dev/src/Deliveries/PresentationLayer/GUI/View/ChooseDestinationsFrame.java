package Deliveries.PresentationLayer.GUI.View;

import Deliveries.BusinessLayer.Site;
import Deliveries.PresentationLayer.GUI.Model.AbstractModel;
import Deliveries.PresentationLayer.GUI.Model.AddDeliveryModel;
import Deliveries.PresentationLayer.GUI.Model.chooseDestinationsModel;

import javax.swing.*;
import java.util.List;

public class ChooseDestinationsFrame extends AbstractFrame {
    public ChooseDestinationsFrame(List<Site> siteList, AddDeliveryModel addDeliveryModel) {
        super(siteList.size()+1, new chooseDestinationsModel(addDeliveryModel));
        addButton("Return to main menu");
        for (Site site : siteList) {
            JCheckBox checkBox = new JCheckBox(site.getName());
            buttonsPanel.add(checkBox);
        }
        addButton("Next");
    }
}
