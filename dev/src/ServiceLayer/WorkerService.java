package ServiceLayer;

import BusinessLayer.Schedule;
import BusinessLayer.Shift;
import BusinessLayer.Worker;

import java.time.LocalDate;
import java.util.Map;

public class WorkerService {
    Worker worker;

    public WorkerService(String name, Integer id, Integer bank_account, Integer salary, String family_status, boolean is_student, String terms_of_employment, LocalDate employment_start_date, Map<String, Schedule> schedules) {
        worker = new Worker(name, id, bank_account, salary, family_status, is_student, terms_of_employment, employment_start_date, schedules);
    }

    public boolean available_to_shift(Shift shift) {
        return worker.available_to_shift(shift);
    }

    public boolean add_role(String role) {
        return worker.add_role(role);
    }

    public boolean remove_role(String role) {
        return worker.remove_role(role);
    }
}
