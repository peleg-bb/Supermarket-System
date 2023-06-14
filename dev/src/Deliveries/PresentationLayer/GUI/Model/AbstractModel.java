package Deliveries.PresentationLayer.GUI.Model;

import Deliveries.PresentationLayer.GUI.View.AbstractFrame;

public abstract class AbstractModel implements java.awt.event.ActionListener{
    protected AbstractFrame relatedFrame;
    public void addFrame(AbstractFrame frame){
        relatedFrame = frame;
    }
}
