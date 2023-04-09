package PresentationLayer;

import ServiceLayer.PersonnelManagerService;
import ServiceLayer.EmployeeService;

import java.time.LocalDate;
import java.util.*;

public class WorkerUI {

    private static Map<Integer, EmployeeService> workers = new HashMap<>();
    private static PersonnelManagerService manager;

    public static void main(String[] args) {
        loadData();
        System.out.println("Welcome to personnel system!");
        System.out.println("Are you a worker or a manager?");
        Scanner ans = new Scanner(System.in);
        String input = ans.nextLine();
        while (!input.equals("worker") && !input.equals("manager") && !input.equals("Worker") && !input.equals("Manager")) {
            System.out.println("Please enter one of the following: worker / manager:");
            input = ans.nextLine();
        }
        if (input.equals("manager") || input.equals("Manager")) {
            while(true) {
                System.out.println("Enter your id: ");
                int id = Integer.parseInt(ans.nextLine());
                System.out.println("Enter your password: ");
                int password = Integer.parseInt(ans.nextLine());
                if (manager.check_password(id, password)) {
                    System.out.println("Logged in Successfully");
                    System.out.println("==========================================================================");
                    break;
                } else {
                    System.out.println("Couldn't log-in. Please try again.");
                    System.out.println("==========================================================================");
                }
            }
            while (true) {
                managerMenu();
                int action = Integer.parseInt(ans.nextLine());
                if (action == 1) {
                    System.out.println("Enter the worker's details: ");
                    if (manager_create_employee(manager)) {
                        System.out.println("Created employee successfully");
                    }
                    else {
                        System.out.println("Couldn't create the employee");
                    }
                    System.out.println("==========================================================================");
                }
                else if (action == 2) {
                    System.out.println("Enter the worker's ID: ");
                    int id = Integer.parseInt(ans.nextLine());
                    if (manager.remove_employee(id)) {
                        System.out.println("Removed employee successfully");
                    }
                    else {
                        System.out.println("Couldn't remove employee");
                    }
                    System.out.println("==========================================================================");
                }
                else if (action == 3) {
                    System.out.println("Enter the worker's ID: ");
                    int id = Integer.parseInt(ans.nextLine());
                    System.out.println("Enter the required role: ");
                    String role = ans.nextLine();
                    System.out.println("Enter the shift's date ({year}-{month}-{day}): ");
                    String date = ans.nextLine();
                    System.out.println("Enter the shift's type (MORNING / EVENING): ");
                    String type = ans.nextLine();
                    System.out.println("Enter the required branch: ");
                    String branch = ans.nextLine();
                    if (manager.assign_to_shift(id, date, type, branch, role)) {
                        System.out.println("Assigned employee successfully");
                    }
                    else {
                        System.out.println("Couldn't assign employee");
                    }
                    System.out.println("==========================================================================");
                }
                else if (action == 4) {
                    System.out.println("Enter the worker's ID: ");
                    int id = Integer.parseInt(ans.nextLine());
                    System.out.println("Enter the required role: ");
                    String role = ans.nextLine();
                    System.out.println("Enter the shift's date ({year}-{month}-{day}): ");
                    String date = ans.nextLine();
                    System.out.println("Enter the shift's type (MORNING / EVENING): ");
                    String type = ans.nextLine();
                    System.out.println("Enter the required branch: ");
                    String branch = ans.nextLine();
                    if (manager.remove_shift(id, date, type, branch, role)) {
                        System.out.println("Remove employee's shift successfully");
                    }
                    else {
                        System.out.println("Couldn't remove employee's shift");
                    }
                    System.out.println("==========================================================================");
                }
                else if (action == 5) {
                    System.out.println("Enter the worker's ID: ");
                    int id = Integer.parseInt(ans.nextLine());
                    System.out.println("Enter the role: ");
                    String role = ans.nextLine();
                    if (manager.add_employee_role(id, role)) {
                        System.out.println("Added employee's role successfully");
                    }
                    else {
                        System.out.println("Couldn't add employee's role");
                    }
                    System.out.println("==========================================================================");
                }
                else if (action == 6) {
                    System.out.println("Enter the worker's ID: ");
                    int id = Integer.parseInt(ans.nextLine());
                    System.out.println("Enter the role: ");
                    String role = ans.nextLine();
                    if (manager.remove_employee_role(id, role)) {
                        System.out.println("Removed employee's role successfully");
                    }
                    else {
                        System.out.println("Couldn't remove employee's role");
                    }
                    System.out.println("==========================================================================");
                }
                else if (action == 7) {
                    System.out.println("Enter the shift's date ({year}-{month}-{day}): ");
                    String date = ans.nextLine();
                    System.out.println("Enter the shift's type (MORNING / EVENING): ");
                    String type = ans.nextLine();
                    System.out.println("Enter the branch: ");
                    String branch = ans.nextLine();
                    if (manager.confirm_shift(date, type, branch)) {
                        System.out.println("Confirmed shift successfully");
                    }
                    else {
                        System.out.println("Couldn't confirm shift");
                    }
                    System.out.println("==========================================================================");
                }
                else if (action == 8) {
                    System.out.println("Enter the branch name: ");
                    String name = ans.nextLine();
                    System.out.println("Enter the branch location: ");
                    String location = ans.nextLine();
                    System.out.println("Enter the branch's morning shift amount of hours: ");
                    int morning_hours = Integer.parseInt(ans.nextLine());
                    System.out.println("Enter the branch's evening shift amount of hours: ");
                    int evening_hours = Integer.parseInt(ans.nextLine());
                    if (manager.create_branch(name, location, morning_hours, evening_hours)) {
                        System.out.println("Created branch successfully");
                    }
                    else {
                        System.out.println("Couldn't create branch");
                    }
                    System.out.println("==========================================================================");
                }
                else if (action == 9) {
                    System.out.println("Enter the branch name: ");
                    String name = ans.nextLine();
                    System.out.println("Enter the schedule's beginning date ({year}-{month}-{day}): ");
                    String date = ans.nextLine();
                    if (manager.create_schedule(name, date)) {
                        System.out.println("Created schedule successfully");
                    }
                    else {
                        System.out.println("Couldn't create schedule");
                    }
                    System.out.println("==========================================================================");
                }
                else if (action == 10) {
                    System.out.println("Enter the worker's id: ");
                    int id = Integer.parseInt(ans.nextLine());
                    System.out.println("Enter the branch: ");
                    String branch = ans.nextLine();
                    if (manager.qualify_employee_to_branch(id, branch)) {
                        System.out.println("Added employee to branch successfully");
                    }
                    else {
                        System.out.println("Couldn't add the employee to this branch");
                    }
                    System.out.println("==========================================================================");
                }
                else if (action == 11) {
                    System.out.println("Enter the worker's id: ");
                    int id = Integer.parseInt(ans.nextLine());
                    System.out.println("Enter the branch: ");
                    String branch = ans.nextLine();
                    System.out.println("Enter the shift's date ({year}-{month}-{day}): ");
                    String date = ans.nextLine();
                    System.out.println("Enter the shift's type (MORNING/EVENING): ");
                    String type = ans.nextLine();
                    if (manager.assign_shift_manager(date, type, branch, id)) {
                        System.out.println("Assigned shift manager successfully");
                    }
                    else {
                        System.out.println("Couldn't assign shift manager");
                    }
                    System.out.println("==========================================================================");
                }
                else if (action == 12) {
                    System.out.println("LOGGING OUT...");
                    break;
                }
            }
        } else if (input.equals("worker") || input.equals("Worker")) {
            EmployeeService worker;
            while(true) {
                System.out.println("Enter your id: ");
                int id = Integer.parseInt(ans.nextLine());
                System.out.println("Enter your password: ");
                int password = Integer.parseInt(ans.nextLine());
                if (manager.check_password(id, password)) {
                    System.out.println("Logged in Successfully");
                    System.out.println("==========================================================================");
                    worker = workers.get(id);
                    break;
                } else {
                    System.out.println("Couldn't log-in. Please try again.");
                    System.out.println("==========================================================================");
                }
            }
            while(true) {
                workerMenu();
                int action = Integer.parseInt(ans.nextLine());
                if (action == 1) {
                    System.out.println("Enter the shift's date ({year}-{month}-{day}): ");
                    String date = ans.nextLine();
                    System.out.println("Enter the branch: ");
                    String branch = ans.nextLine();
                    System.out.println("Enter the shift's type (MORNING/EVENING): ");
                    String type = ans.nextLine();
                    if (worker.available_to_shift(date, branch, type)) {
                        System.out.println("Added available shift successfully!");
                    } else {
                        System.out.println("Couldn't add availability");
                    }
                    System.out.println("===========================================================");
                } else if (action == 2) {
                    System.out.println("Enter the shift's date ({year}-{month}-{day}): ");
                    String date = ans.nextLine();
                    System.out.println("Enter the branch: ");
                    String branch = ans.nextLine();
                    System.out.println("Enter the shift's type (MORNING/EVENING): ");
                    String type = ans.nextLine();
                    if (worker.remove_availability(date, branch, type)) {
                        System.out.println("Removed available shift successfully!");
                    } else {
                        System.out.println("Couldn't remove availability");
                    }
                    System.out.println("===========================================================");
                }
                else if (action == 3) {
                    System.out.println("Which branch do you wish to check availability at? ");
                    String branch = ans.nextLine();
                    Map<String, String> availability = worker.getAvailability(branch);
                    if (availability.isEmpty()) {
                        System.out.println("You have no shifts availability yet!");
                        System.out.println("===========================================================");
                        continue;
                    }
                    int counter = 1;
                    for (String date: availability.keySet()) {
                        System.out.println(counter + ". " + date + " - " + availability.get(date));
                        counter++;
                    }
                    System.out.println("===========================================================");
                }
                else if (action == 4) {
                    System.out.println("Which branch do you wish to check shifts at? ");
                    String branch = ans.nextLine();
                    Map<String, String> shifts = worker.getShifts(branch);
                    if (shifts.isEmpty()) {
                        System.out.println("You have no shifts yet!");
                        System.out.println("===========================================================");
                        continue;
                    }
                    int counter = 1;
                    for (String date: shifts.keySet()) {
                        System.out.println(counter + ". " + date + " - " + shifts.get(date));
                        counter++;
                    }
                    System.out.println("===========================================================");
                }

                else if (action == 5) {
                    System.out.println("LOGGING OUT...");
                    break;
                }
            }
        }
    }

    public static boolean manager_create_employee(PersonnelManagerService manager) {
        Scanner ans = new Scanner(System.in);
        System.out.println("Enter the employee's name: ");
        String name = ans.next();
        System.out.println("Enter the employee's id: ");
        int id = Integer.parseInt(ans.nextLine());
        System.out.println("Enter the employee's bank account number: ");
        int bank_account = Integer.parseInt(ans.nextLine());
        System.out.println("Enter the employee's salary: ");
        int salary = Integer.parseInt(ans.nextLine());
        System.out.println("Enter the employee's family status: ");
        String family_status = ans.next();
        System.out.println("Is she/he a student? ");
        String is_student = ans.next();
        System.out.println("Enter the employee's employment start date ({year}-{month}-{day}): ");
        String employment_start_date = ans.next();
        boolean student;
        if (is_student.equals("yes")) {
            student = true;
        }
        else {
            student = false;
        }
        System.out.println("Enter the employee's terms of employment: ");
        String terms_of_employment = ans.next();
        System.out.println("Enter the employee's password: ");
        int password = Integer.parseInt(ans.next());
        return manager.add_employee(name, id, bank_account, salary, family_status, student, terms_of_employment, employment_start_date, password);
    }

    public static void workerMenu() {
        System.out.println("Current Week: 2023-04-02 - 2023-04-08, available branches: Tel Aviv, Be'er Sheva \n");
        System.out.println("Please enter the number of the option you would like to choose: ");
        System.out.println("1. Add availability to a shift");
        System.out.println("2. Remove availability from a shift");
        System.out.println("3. Show my shifts availability");
        System.out.println("4. Show my shifts");
        System.out.println("5. Exit");
    }

    public static void managerMenu() {
        System.out.println("Current Week: 2023-04-02 - 2023-04-08, available branches: Tel Aviv, Be'er Sheva \n");
        System.out.println("Please enter the number of the option you would like to choose: ");
        System.out.println("1. Add a worker");
        System.out.println("2. Remove a worker");
        System.out.println("3. Assign to a shift");
        System.out.println("4. Remove from a shift");
        System.out.println("5. Add employee role");
        System.out.println("6. Remove employee role");
        System.out.println("7. Confirm a shift");
        System.out.println("8. Create a branch");
        System.out.println("9. Create a schedule to a branch");
        System.out.println("10. Qualify employee to a branch");
        System.out.println("11. Assign shift manager");
        System.out.println("12. Exit");
    }

    /* Loading an example data */
    public static void loadData() {
        PersonnelManagerService manager = new PersonnelManagerService("Omer Guz", 555555555, 567567, 70, "Married", false, "", LocalDate.of(2023, 1, 1), 123456);
        WorkerUI.manager = manager;
        manager.create_branch("Be'er Sheva", "Be'er Sheva", 7,8);
        manager.create_branch("Tel Aviv", "Tel Aviv", 6,8);
        manager.create_schedule("Tel Aviv", "2023-04-02");
        manager.create_schedule("Be'er Sheva", "2023-04-02");
        manager.add_employee("Ron Hadad", 111111111, 123123, 30, "Single", true, "Commited to one year", "2023-01-22", 111111);
        manager.qualify_employee_to_branch(111111111, "Be'er Sheva");
        manager.qualify_employee_to_branch(111111111, "Tel Aviv");
        manager.add_employee("Guy Cohen", 222222222, 234234, 35, "Married", false, "", "2023-02-01", 222222);
        manager.qualify_employee_to_branch(222222222, "Be'er Sheva");
        manager.add_employee("Tal Levi", 333333333, 345345, 34, "Single", true, "Can't work on Thursdays", "2023-09-26", 333333);
        manager.qualify_employee_to_branch(333333333, "Tel Aviv");
        manager.add_employee("Ron Zehavi", 444444444, 456456, 33, "Single", true, "Commited to one year", "2023-02-02", 444444);
        manager.add_employee_role(111111111, "Storekeeper");
        manager.add_employee_role(111111111, "Cashier");
        manager.add_employee_role(222222222, "Security");
        manager.add_employee_role(333333333, "General");
        EmployeeService worker1 = new EmployeeService(manager.getEmployee(111111111));
        workers.put(111111111, worker1);
        EmployeeService worker2 = new EmployeeService(manager.getEmployee(222222222));
        workers.put(222222222, worker2);
        EmployeeService worker3 = new EmployeeService(manager.getEmployee(333333333));
        workers.put(333333333, worker3);
        EmployeeService worker4 = new EmployeeService(manager.getEmployee(444444444));
        workers.put(444444444, worker4);
    }
}
