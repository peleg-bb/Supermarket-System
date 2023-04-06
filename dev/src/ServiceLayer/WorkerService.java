package ServiceLayer;

import BusinessLayer.Shift;
import BusinessLayer.Worker;

import java.time.LocalDate;

public class WorkerService {
    Worker worker;

    public WorkerService(String name, Integer id, Integer bank_account, Integer salary, String family_status, boolean is_student, String terms_of_employment, LocalDate employment_start_date) {
        worker = new Worker(name, id, bank_account, salary, family_status, is_student, terms_of_employment, employment_start_date);
    }

    public WorkerService(Worker worker) {
        this.worker = worker;
    }

    public boolean available_to_shift(String date, String branch, String type) {
        return worker.available_to_shift(date, branch, type);
    }

    public boolean remove_availability(String date, String branch, String type) {
        return worker.remove_availability(date, branch, type);
    }

    public int getID() {
        return worker.getID();
    }
}
