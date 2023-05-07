package HR_Deliveries_Interface;public interface DriverFactory {
    boolean AddDriverToSystem(String name, String id, String phone, int maxWeight, boolean regularAllowed,
                              boolean refrigeratedAllowed);
}
