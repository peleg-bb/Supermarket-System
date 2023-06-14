package Deliveries.PresentationLayer.GUI.Model;

import Deliveries.PresentationLayer.GUI.View.MainMenuFrame;

import java.awt.event.ActionEvent;

public class ExecuteDeliveriesModel extends AbstractModel {
    public ExecuteDeliveriesModel() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Return to main menu")) {
            ReturnToMainMenuClicked();
        }
        else {
            // TODO: Implement this - execute the delivery clicked
        }

    }

    private void ReturnToMainMenuClicked() {
        relatedFrame.dispose();
        new MainMenuFrame();
    }
}
