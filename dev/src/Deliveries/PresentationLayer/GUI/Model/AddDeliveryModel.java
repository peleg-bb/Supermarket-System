package Deliveries.PresentationLayer.GUI.Model;

import Deliveries.BusinessLayer.DeliveryFormsController;
import Deliveries.BusinessLayer.Enums_and_Interfaces.TruckType;
import Deliveries.BusinessLayer.Site;
import Deliveries.PresentationLayer.GUI.View.ChooseDestinationsFrame;
import Deliveries.PresentationLayer.GUI.View.chooseItemsFrame;

import javax.swing.*;
import java.util.List;

public class AddDeliveryModel extends AbstractModel {
    DeliveryFormsController deliveryFormsController;
    private Site origin;
    private List<Site> destinations;
    private TruckType truckType;
    private final List<Site> sitesList;
    public AddDeliveryModel(List<Site> sitesList) {
            deliveryFormsController = DeliveryFormsController.getInstance();
            this.sitesList = sitesList;
        }
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getActionCommand().equals("Return to main menu")) {
                ReturnToMainMenuClicked();
            }
            else {
                String buttonText = e.getActionCommand();
                handleOriginClicked(buttonText);
                relatedFrame.setVisible(false);
                new ChooseDestinationsFrame(sitesList, this);
                addDelivery(buttonText);
            }
        }


    private void handleOriginClicked(String buttonText) {
        for (Site site : sitesList) {
            if (site.getName().equals(buttonText)) {
                origin = site;
                break;
            }
        }
    }

    private void addDelivery(String buttonText) {
    

    // Do the rest of the delivery creation logic here
    }

    public void receiveDestinations(List<String> destinations) {
        for (String destination : destinations) {
            for (Site site : sitesList) {
                if (site.getName().equals(destination)) {
                    this.destinations.add(site);
                    break;
                }
            }
        }
        continueLogic();
    }

    private void continueLogic() {
        relatedFrame.clearButtonsPanel();
        relatedFrame.setVisible(true);

        chooseTruckType();


        // TODO: Continue with the rest of the delivery creation logic
    }

    private void chooseTruckType() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("Regular");
        comboBox.addItem("Refrigerated");
        JOptionPane.showMessageDialog(null, comboBox, "Choose truck type",
                JOptionPane.QUESTION_MESSAGE);
        String truckType = (String) comboBox.getSelectedItem();
        if (truckType == null) {
            relatedFrame.displayError("Invalid truck type selected!");
            chooseTruckType();
        }
        else {
            this.truckType = TruckType.valueOf(truckType.toUpperCase());
            new chooseItemsFrame(this);
        }

    }
}

