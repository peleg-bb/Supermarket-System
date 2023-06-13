package Deliveries.PresentationLayer.GUI.View;

import Deliveries.PresentationLayer.GUI.Model.MainMenuModel;

public class MainFrame extends AbstractFrame {
    public MainFrame() {
        super(4, new MainMenuModel());
        addBottom(e -> {}, "Add a delivery stop");
        addBottom(e -> {}, "Remove a delivery stop");
        addBottom(e -> {}, "Execute a delivery");
        addBottom(e -> {}, "Exit");
  }

}