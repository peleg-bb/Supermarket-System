package ServiceLayer;

import BusinessLayer.Shift;
import BusinessLayer.Worker;

import java.time.LocalDate;

public class WorkerService {
    Worker worker;

    public WorkerService(String name, Integer id, Integer bank_account, Integer salary, String family_status, boolean is_student, String terms_of_employment, LocalDate employment_start_date) {
        worker = new Worker(name, id, bank_account, salary, family_status, is_student, terms_of_employment, employment_start_date);
    }

    public boolean available_to_shift(LocalDate date, String branch, Shift.shift_type type) {
        return worker.available_to_shift(date, branch, type);
    }

    public boolean add_role(String role) {
        return worker.add_role(role);
    }

    public boolean remove_role(String role) {
        return worker.remove_role(role);
    }
}
