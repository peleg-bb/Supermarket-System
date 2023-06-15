package Deliveries.PresentationLayer.GUI.Model;

import Deliveries.BusinessLayer.DeliveryFormsController;

import java.awt.event.ActionEvent;

public class ExecuteDeliveriesModel extends AbstractModel {
    DeliveryFormsController deliveryFormsController;
    public ExecuteDeliveriesModel() {
        deliveryFormsController = DeliveryFormsController.getInstance();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Return to main menu")) {
            ReturnToMainMenuClicked();
        }
        else {
//            String buttonText = e.getActionCommand();
//            Pattern pattern = Pattern.compile("\\d+");
//            Matcher matcher = pattern.matcher(buttonText);
//            if (matcher.find()) {
//                int formId = Integer.parseInt(matcher.group());
//                DeliveryForm form = deliveryFormsController.getDeliveryForm(formId);
//                relatedFrame.dispose();
//                new MakeDeliveryFrame(form);
            String buttonText = e.getActionCommand();
            executeDelivery(buttonText);
            }


        }
    private void executeDelivery(String buttonText) {
        // Extract the delivery ID from the button text
        String deliveryId = buttonText.substring(6); // Assuming the button text is in the format "FormX"

        // Perform the execution of the delivery based on the delivery ID
        // TODO: Implement the logic to execute the delivery

        // Show a message dialog indicating the execution
        String message = "Executing delivery with ID: " + deliveryId;
        relatedFrame.displayInfo(message);
        //JOptionPane.showMessageDialog(null, message);
    }
    }


