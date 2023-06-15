package Deliveries.PresentationLayer.GUI.Model;

import Deliveries.BusinessLayer.DeliveryFormsController;

public class AddDeliveryModel extends AbstractModel{
    DeliveryFormsController deliveryFormsController;
public AddDeliveryModel() {
        deliveryFormsController = DeliveryFormsController.getInstance();
    }
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (e.getActionCommand().equals("Return to main menu")) {
            ReturnToMainMenuClicked();
        }
        else{
            String buttonText = e.getActionCommand();
            addDelivery(buttonText);
        }
    }

    private void addDelivery(String buttonText) {
        // Extract the delivery ID from the button text
        String siteId = buttonText.substring(0,1); // Assuming the button text is in the format "FormX"

        // Perform the execution of the delivery based on the delivery ID
        relatedFrame.displayInfo("Origin is set! Please enter the details about the destinations");
    }
}
