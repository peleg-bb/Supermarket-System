package Deliveries.DeliveriesTests;

import Deliveries.DeliveryForm;
import Deliveries.WeightMeasurer;

public class MockWeightMeasurer implements WeightMeasurer {
    public MockWeightMeasurer() {
    }
    @Override
    public int measureWeight(DeliveryForm form) {
        return 5;
    }
}
