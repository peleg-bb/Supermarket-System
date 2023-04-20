package Deliveries.DeliveriesTests;

import Deliveries.BusinessLayer.DeliveryForm;
import Deliveries.BusinessLayer.Enums_and_Interfaces.WeightMeasurer;

public class MockWeightMeasurer implements WeightMeasurer {
    public MockWeightMeasurer() {
    }
    @Override
    public int measureWeight(DeliveryForm form) {
        return 5;
    }
}
