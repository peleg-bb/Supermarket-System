package HR.PresentationLayer;

import HR.ServiceLayer.EmployeeService;
import HR.ServiceLayer.Response;

import java.util.Date;
import java.util.Scanner;

public class HRMenu {

    private final EmployeeService service;

    public HRMenu() {
        service = new EmployeeService();
    }

    public String print_red(String text) {
        return "\033[1;31m" + text + "\u001B[0m";
    }

    public String print_green(String text) {
        return "\033[1;32m" + text + "\u001B[0m";
    }

    public String print_blue(String text) {
        return "\033[34m" + text + "\u001B[0m";
    }

    public void run() {
        service.load_data();
        System.out.println("Welcome to HR system!");
        while(true) {
            while (!service.is_logged_in()) {
                login_screen();
            }
            menu();
        }
    }

    public void login_screen() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your id: ");
        String id = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        Response res = service.login(id, password);
        while(res.errorOccurred()) {
            System.out.println(print_red(res.getErrorMessage()));
            System.out.println("Please try again:\n");
            System.out.println("Enter your id: ");
            id = scanner.nextLine();
            System.out.println("Enter your password: ");
            password = scanner.nextLine();
            res = service.login(id, password);
        }
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while(service.is_logged_in()) {
            if (service.is_hr()) {
                manager_options();
                String input = scanner.nextLine();
                handle_manager_action(input);
            } else {
                employee_options();
                String input = scanner.nextLine();
                handle_employee_action(input);
            }
        }
    }

    private void handle_employee_action(String input) {
        Scanner scanner = new Scanner(System.in);
        switch (input) {
            case "1": {
                System.out.println("=====================================================================================");
                System.out.print("Please enter the shift's date (dd-mm-yyyy): ");
                String date = scanner.nextLine();
                System.out.print("Please enter the shift's type (morning / evening): ");
                String shift_type = scanner.nextLine();
                System.out.print("Please enter the availability store / \"drivers\": ");
                String store = scanner.nextLine();
                Response res = service.add_availability(date, shift_type, store);
                if (res.errorOccurred()) {
                    System.out.println("\n");
                    System.out.println(print_red(res.getErrorMessage()));
                } else {
                    System.out.println("\n");
                    System.out.println(print_green("Added availability successfully!"));
                }
                System.out.println("=====================================================================================");
                break;
            }
            case "2": {
                System.out.println("=====================================================================================");
                System.out.print("Please enter the shift's date (dd-mm-yyyy): ");
                String date = scanner.nextLine();
                System.out.print("Please enter the shift's type (morning / evening): ");
                String shift_type = scanner.nextLine();
                System.out.print("Please enter the availability store / \"drivers\": ");
                String store = scanner.nextLine();
                Response res = service.remove_availability(date, shift_type, store);
                if (res.errorOccurred()) {
                    System.out.println("\n");
                    System.out.println(print_red(res.getErrorMessage()));
                } else {
                    System.out.println("\n");
                    System.out.println(print_green("Removed availability successfully!"));
                }
                System.out.println("=====================================================================================");
                break;
            }
            case "3":
                System.out.println("=====================================================================================");
                System.out.println("Your shifts:");
                System.out.println(service.get_shifts().getErrorMessage());
                System.out.println("=====================================================================================");
                break;
            case "4":
                System.out.println("=====================================================================================");
                System.out.println("Your availability:");
                System.out.println(service.get_availability().getErrorMessage());
                System.out.println("=====================================================================================");
                break;
            case "5":
                System.out.println("=====================================================================================");
                System.out.println(service.show_personal_info().getErrorMessage());
                System.out.println("\nWhich of the following do you wish to change?");
                System.out.println("1. Name");
                System.out.println("2. Bank Account");
                System.out.println("3. Family Status");
                System.out.println("4. Student Status");
                System.out.print("Option: ");
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1": {
                        System.out.println("=====================================================================================");
                        System.out.print("Please enter your old name: ");
                        String old_name = scanner.nextLine();
                        System.out.print("Please enter your new name: ");
                        String new_name = scanner.nextLine();
                        Response res = service.change_name(old_name, new_name);
                        if (res.errorOccurred()) {
                            System.out.println("\n");
                            System.out.println(print_red(res.getErrorMessage()));
                        } else {
                            System.out.println("\n");
                            System.out.println(print_green("Changed your name successfully"));
                        }
                        System.out.println("=====================================================================================");
                        break;
                    }
                    case "2": {
                        System.out.println("=====================================================================================");
                        System.out.print("Please enter your old bank account: ");
                        String old_bank_account = scanner.nextLine();
                        System.out.print("Please enter your new bank account: ");
                        String new_bank_account = scanner.nextLine();
                        Response res = service.change_bank_account(old_bank_account, new_bank_account);
                        if (res.errorOccurred()) {
                            System.out.println("\n");
                            System.out.println(print_red(res.getErrorMessage()));
                        } else {
                            System.out.println("\n");
                            System.out.println(print_green("Changed your bank account successfully"));
                        }
                        System.out.println("=====================================================================================");
                        break;
                    }
                    case "3": {
                        System.out.println("=====================================================================================");
                        System.out.print("Please enter your old family status: ");
                        String old_family_status = scanner.nextLine();
                        System.out.print("Please enter your new family status: ");
                        String new_family_status = scanner.nextLine();
                        Response res = service.change_family_status(old_family_status, new_family_status);
                        if (res.errorOccurred()) {
                            System.out.println("\n");
                            System.out.println(print_red(res.getErrorMessage()));
                        } else {
                            System.out.println("\n");
                            System.out.println(print_green("Changed your family status successfully"));
                        }
                        System.out.println("=====================================================================================");
                        break;
                    }
                    case "4": {
                        System.out.println("=====================================================================================");
                        System.out.print("Were you a student before (yes / no)? ");
                        String old_student_status = scanner.nextLine();
                        System.out.print("Are you a student now (yes / no)? ");
                        String new_student_status = scanner.nextLine();
                        Response res = service.change_student(old_student_status, new_student_status);
                        if (res.errorOccurred()) {
                            System.out.println("\n");
                            System.out.println(print_red(res.getErrorMessage()));
                        } else {
                            System.out.println("\n");
                            System.out.println(print_green("Changed your student status successfully"));
                        }
                        System.out.println("=====================================================================================");
                        break;
                    }
                }
                System.out.println("=====================================================================================");
                break;
            case "6":
                System.out.println("=====================================================================================");
                System.out.print("Certified roles: ");
                System.out.println(service.show_role_certifications().getErrorMessage());
                System.out.println("=====================================================================================");
                break;
            case "7":
                System.out.println("=====================================================================================");
                System.out.print("Assigned stores: ");
                System.out.println(service.show_assigned_stores().getErrorMessage());
                System.out.println("=====================================================================================");
                break;
            case "8":
                System.out.println("=====================================================================================");
                System.out.println("Personal information:");
                System.out.println(service.show_personal_info().getErrorMessage());
                System.out.println("=====================================================================================");
                break;
            case "9":
                System.out.println("=====================================================================================");
                System.out.print("Current salary: ");
                System.out.println(service.show_current_salary().getErrorMessage());
                System.out.println("=====================================================================================");
                break;
            case "10": {
                System.out.println("=====================================================================================");
                System.out.print("Please enter the shift's date (dd-mm-yyyy): ");
                String date = scanner.nextLine();
                System.out.print("Please enter the shift's type (morning / evening): ");
                String shift_type = scanner.nextLine();
                System.out.print("Please enter the store: ");
                String store = scanner.nextLine();
                System.out.print("Please enter the product id: ");
                String product = scanner.nextLine();
                Response res = service.cancel_product(product, date, shift_type, store);
                if (res.errorOccurred()) {
                    System.out.println("\n");
                    System.out.println(print_red(res.getErrorMessage()));
                } else {
                    System.out.println("\n");
                    System.out.println(print_green("Cancelled product successfully"));
                }
                System.out.println("=====================================================================================");
                break;
            }
            case "11":
                System.out.println("=====================================================================================");
                Response res = service.logout();
                if (res.errorOccurred()) {
                    System.out.println("\n");
                    System.out.println(print_red(res.getErrorMessage()));
                }
                System.out.println("=====================================================================================");
                break;
            default:
                System.out.println(print_red("You've chosen an invalid option. Please choose again."));
                break;
        }
    }

    private void handle_manager_action(String input) {
        Scanner scanner = new Scanner(System.in);
        switch (input) {
            case "1": {
                System.out.println("=====================================================================================");
                System.out.print("Please enter the shift's date (dd-mm-yyyy): ");
                String date = scanner.nextLine();
                System.out.print("Please enter the shift's type (morning / evening): ");
                String shift_type = scanner.nextLine();
                System.out.print("Please enter the store / \"drivers\": ");
                String store = scanner.nextLine();
                System.out.print("Available employees for this shift: ");
                System.out.println("\n");
                System.out.println(service.show_shift_availability(date, shift_type, store).getErrorMessage());
                System.out.println("\n");
                System.out.print("Please enter the employee's id: ");
                String id = scanner.nextLine();
                System.out.print("Please enter the role: ");
                String role = scanner.nextLine();
                Response res = service.assign_shift(id, date, shift_type, store, role);
                if (res.errorOccurred()) {
                    System.out.println("\n");
                    System.out.println(print_red(res.getErrorMessage()));
                }
                else {
                    System.out.println("\n");
                    System.out.println(print_green("Assigned employee successfully"));
                }
                System.out.println("=====================================================================================");
                break;
            }
            case "2": {
                System.out.println("=====================================================================================");
                System.out.print("Please enter the shift's date (dd-mm-yyyy): ");
                String date = scanner.nextLine();
                System.out.print("Please enter the shift's type (morning / evening): ");
                String shift_type = scanner.nextLine();
                System.out.print("Please enter the store / \"drivers\": ");
                String store = scanner.nextLine();
                System.out.print("Please enter the employee's id: ");
                String id = scanner.nextLine();
                System.out.print("Please enter the role: ");
                String role = scanner.nextLine();
                Response res = service.unassign_shift(id, date, shift_type, store, role);
                if (res.errorOccurred()) {
                    System.out.println("\n");
                    System.out.println(print_red(res.getErrorMessage()));
                }
                else {
                    System.out.println("\n");
                    System.out.println(print_green("Removed employee from the shift successfully"));
                }
                System.out.println("=====================================================================================");
                break;
            }
            case "3": {
                System.out.println("=====================================================================================");
                System.out.print("Please enter the employee's id: ");
                String id = scanner.nextLine();
                System.out.print("Please enter the role: ");
                String role = scanner.nextLine();
                Response res = service.certify_role(id, role);
                if (res.errorOccurred()) {
                    System.out.println("\n");
                    System.out.println(print_red(res.getErrorMessage()));
                }
                else {
                    System.out.println("\n");
                    System.out.println(print_green("Added a role certification successfully"));
                }
                System.out.println("=====================================================================================");
                break;
            }
            case "4": {
                System.out.println("=====================================================================================");
                System.out.print("Please enter the employee's id: ");
                String id = scanner.nextLine();
                System.out.print("Please enter the role: ");
                String role = scanner.nextLine();
                Response res = service.remove_role(id, role);
                if (res.errorOccurred()) {
                    System.out.println("\n");
                    System.out.println(print_red(res.getErrorMessage()));
                }
                else {
                    System.out.println("\n");
                    System.out.println(print_green("Removed a role certification successfully"));
                }
                System.out.println("=====================================================================================");
                break;
            }
            case "5": {
                System.out.println("=====================================================================================");
                System.out.print("Please enter the employee's id: ");
                String id = scanner.nextLine();
                System.out.print("Please enter the store: ");
                String store = scanner.nextLine();
                Response res = service.assign_to_store(id, store);
                if (res.errorOccurred()) {
                    System.out.println("\n");
                    System.out.println(print_red(res.getErrorMessage()));
                }
                else {
                    System.out.println("\n");
                    System.out.println(print_green("Assigned the employee to a store successfully"));
                }
                System.out.println("=====================================================================================");
                break;
            }
            case "6": {
                System.out.println("=====================================================================================");
                System.out.print("Please enter the employee's id: ");
                String id = scanner.nextLine();
                System.out.print("Please enter the store: ");
                String store = scanner.nextLine();
                Response res = service.unassign_to_store(id, store);
                if (res.errorOccurred()) {
                    System.out.println("\n");
                    System.out.println(print_red(res.getErrorMessage()));
                }
                else {
                    System.out.println("\n");
                    System.out.println(print_green("Removed the employee from a store successfully"));
                }
                System.out.println("=====================================================================================");
                break;
            }
            case "7": {
                System.out.println("=====================================================================================");
                System.out.print("Please enter the employee's id: ");
                String id = scanner.nextLine();
                System.out.print("Please enter the employee's name: ");
                String name = scanner.nextLine();
                System.out.print("Please enter the employee's bank account: ");
                String bank_account = scanner.nextLine();
                System.out.print("Please enter the employee's salary: ");
                String salary = scanner.nextLine();
                System.out.print("Please enter the employee's terms of employment: ");
                String terms_of_employment = scanner.nextLine();
                System.out.print("Please enter the employment date (dd-mm-yyyy): ");
                String employment_date = scanner.nextLine();
                System.out.print("Please enter the employee's family status: ");
                String family_status = scanner.nextLine();
                System.out.print("Is the employee a student? ");
                String is_student = scanner.nextLine();
                System.out.print("Please enter the employee's password: ");
                String password = scanner.nextLine();
                Response res = service.add_employee(id, name, bank_account, salary, terms_of_employment, employment_date, family_status, is_student, password);
                if (res.errorOccurred()) {
                    System.out.println("\n");
                    System.out.println(print_red(res.getErrorMessage()));
                }
                else {
                    System.out.println("\n");
                    System.out.println(print_green("Added the employee successfully"));
                }
                System.out.println("=====================================================================================");
                break;
            }
            case "8": {
                System.out.println("=====================================================================================");
                System.out.print("Please enter the employee's id: ");
                String id = scanner.nextLine();
                Response res = service.remove_employee(id);
                if (res.errorOccurred()) {
                    System.out.println("\n");
                    System.out.println(print_red(res.getErrorMessage()));
                }
                else {
                    System.out.println("\n");
                    System.out.println(print_green("Removed the employee successfully"));
                }
                System.out.println("=====================================================================================");
                break;
            }
            case "9": {
                System.out.println("=====================================================================================");
                System.out.print("Please enter the store's name: ");
                String store = scanner.nextLine();
                Response res = service.create_store(store);
                if (res.errorOccurred()) {
                    System.out.println("\n");
                    System.out.println(print_red(res.getErrorMessage()));
                }
                else {
                    System.out.println("\n");
                    System.out.println(print_green("Created the store successfully"));
                }
                System.out.println("=====================================================================================");
                break;
            }
            case "10": {
                System.out.println("=====================================================================================");
                System.out.print("Please enter the store's name: ");
                String store = scanner.nextLine();
                Response res = service.remove_store(store);
                if (res.errorOccurred()) {
                    System.out.println("\n");
                    System.out.println(print_red(res.getErrorMessage()));
                }
                else {
                    System.out.println("\n");
                    System.out.println(print_green("Removed the store successfully"));
                }
                System.out.println("=====================================================================================");
                break;
            }
            case "11": {
                System.out.println("=====================================================================================");
                System.out.print("Please enter the shift's date (dd-mm-yyyy): ");
                String date = scanner.nextLine();
                System.out.print("Please enter the store's name / \"drivers\": ");
                String store = scanner.nextLine();
                System.out.print("Please enter the shift's type (morning / evening): ");
                String shift_type = scanner.nextLine();
                Response res = service.confirm_shift(date, shift_type, store);
                if (res.errorOccurred()) {
                    System.out.println("\n");
                    System.out.println(print_red(res.getErrorMessage()));
                }
                else {
                    System.out.println("\n");
                    System.out.println(print_green("Confirmed the shift successfully"));
                }
                System.out.println("=====================================================================================");
                break;
            }
            case "12": {
                System.out.println("=====================================================================================");
                System.out.print("Please enter the week's first day date (dd-mm-yyyy): ");
                String date = scanner.nextLine();
                System.out.print("Please enter the store's name / \"drivers\": ");
                String store = scanner.nextLine();
                System.out.print("Please enter the schedule's morning shifts start time (hh:mm): ");
                String morn_start = scanner.nextLine();
                System.out.print("Please enter the schedule's morning shifts end time (hh:mm): ");
                String morn_end = scanner.nextLine();
                System.out.print("Please enter the schedule's evening shifts start time (hh:mm): ");
                String eve_start = scanner.nextLine();
                System.out.print("Please enter the schedule's evening shifts end time (hh:mm): ");
                String eve_end = scanner.nextLine();
                Response res = service.create_weekly_schedule(date, store, morn_start, morn_end, eve_start, eve_end);
                if (res.errorOccurred()) {
                    System.out.println("\n");
                    System.out.println(print_red(res.getErrorMessage()));
                }
                else {
                    System.out.println("\n");
                    System.out.println(print_green("Created the schedule successfully"));
                }
                System.out.println("=====================================================================================");
                break;
            }
            case "13": {
                System.out.println("=====================================================================================");
                System.out.print("Please enter the employee's id: ");
                String id = scanner.nextLine();
                System.out.print("Please enter the shift's date (dd-mm-yyyy): ");
                String date = scanner.nextLine();
                System.out.print("Please enter the shift's type (morning / evening): ");
                String shift_type = scanner.nextLine();
                System.out.print("Please enter the availability store / \"drivers\": ");
                String store = scanner.nextLine();
                Response res = service.limit_work(id, date, shift_type, store);
                if (res.errorOccurred()) {
                    System.out.println("\n");
                    System.out.println(print_red(res.getErrorMessage()));
                } else {
                    System.out.println("\n");
                    System.out.println(print_green("Added employee's limitation successfully!"));
                }
                System.out.println("=====================================================================================");
                break;
            }
            case "14": {
                System.out.println("=====================================================================================");
                System.out.print("Please enter the employee's id: ");
                String id = scanner.nextLine();
                System.out.print("Please enter the shift's date (dd-mm-yyyy): ");
                String date = scanner.nextLine();
                System.out.print("Please enter the shift's type (morning / evening): ");
                String shift_type = scanner.nextLine();
                System.out.print("Please enter the availability store / \"drivers\": ");
                String store = scanner.nextLine();
                Response res = service.remove_worker_limit(id, date, shift_type, store);
                if (res.errorOccurred()) {
                    System.out.println("\n");
                    System.out.println(print_red(res.getErrorMessage()));
                } else {
                    System.out.println("\n");
                    System.out.println(print_green("Removed employee's limitation successfully!"));
                }
                System.out.println("=====================================================================================");
                break;
            }
            case "15": {
                System.out.println("=====================================================================================");
                System.out.print("Please enter the employee's id: ");
                String id = scanner.nextLine();
                System.out.print("Please enter a bonus for the employee / 0 if none: ");
                String bonus = scanner.nextLine();
                Response res = service.confirm_monthly_salary(id, bonus);
                if (res.errorOccurred()) {
                    System.out.println("\n");
                    System.out.println(print_red(res.getErrorMessage()));
                }
                else {
                    System.out.println("\n");
                    System.out.println(print_green("Confirmed the employee's salary successfully!"));
                }
                System.out.println("=====================================================================================");
                break;
            }
            case "16": {
                System.out.println("=====================================================================================");
                System.out.println("\nWhich of the following do you wish to change?");
                System.out.println("1. Salary");
                System.out.println("2. Terms of Employment");
                System.out.print("Option: ");
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1": {
                        System.out.println("=====================================================================================");
                        System.out.print("Please enter the employee's id: ");
                        String id = scanner.nextLine();
                        System.out.print("Please enter the employee's old salary: ");
                        String old_salary = scanner.nextLine();
                        System.out.print("Please enter the employee's new salary: ");
                        String new_salary = scanner.nextLine();
                        Response res = service.change_employee_salary(id, old_salary, new_salary);
                        if (res.errorOccurred()) {
                            System.out.println("\n");
                            System.out.println(print_red(res.getErrorMessage()));
                        } else {
                            System.out.println("\n");
                            System.out.println(print_green("Changed the employee's salary successfully"));
                        }
                        System.out.println("=====================================================================================");
                        break;
                    }
                    case "2": {
                        System.out.println("=====================================================================================");
                        System.out.print("Please enter the employee's id: ");
                        String id = scanner.nextLine();
                        System.out.print("Please enter the employee's new terms of employment account: ");
                        String new_terms = scanner.nextLine();
                        Response res = service.change_employee_terms(id, new_terms);
                        if (res.errorOccurred()) {
                            System.out.println("\n");
                            System.out.println(print_red(res.getErrorMessage()));
                        } else {
                            System.out.println("\n");
                            System.out.println(print_green("Changed the employee's terms of employment successfully"));
                        }
                        System.out.println("=====================================================================================");
                        break;
                    }
                }
                System.out.println("=====================================================================================");
                break;
            }
            case "17": {
                System.out.println("=====================================================================================");
                Response res = service.show_employees();
                if (!res.errorOccurred()) {
                    System.out.println("\n");
                    System.out.println(print_red("No employees!"));
                    break;
                }
                System.out.println(res.getErrorMessage());
                System.out.println("=====================================================================================");
                break;
            }
            case "18": {
                System.out.println("=====================================================================================");
                System.out.print("Please enter the employee's id: ");
                String id = scanner.nextLine();
                Response res = service.show_employee_info(id);
                System.out.println(print_green("Employee's Information:"));
                System.out.println(res.getErrorMessage());
                System.out.println("=====================================================================================");
                break;
            }
            case "19": {
                System.out.println("=====================================================================================");
                Response res = service.logout();
                if (res.errorOccurred()) {
                    System.out.println(res.getErrorMessage());
                }
                System.out.println("=====================================================================================");
                break;
            }

        }
    }

    public void employee_options() {
        System.out.println(print_blue("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
        System.out.println(print_blue("Choose one of the options:"));
        System.out.println(print_blue("1. Add an availability"));
        System.out.println(print_blue("2. Remove an availability"));
        System.out.println(print_blue("3. Show my shifts"));
        System.out.println(print_blue("4. Show my availability"));
        System.out.println(print_blue("5. Change personal information"));
        System.out.println(print_blue("6. Show my role certifications"));
        System.out.println(print_blue("7. Show my assigned stores"));
        System.out.println(print_blue("8. Show my personal information"));
        System.out.println(print_blue("9. Show my current expected salary"));
        System.out.println(print_blue("10. Cancel a product"));
        System.out.println(print_blue("11. Logout"));
        System.out.println(print_blue("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
        System.out.print(print_blue("Option:"));
    }

    public void manager_options() {
        System.out.println(print_blue("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
        System.out.println(print_blue("Choose one of the options:"));
        System.out.println(print_blue("1. Assign an employee to a shift"));
        System.out.println(print_blue("2. Remove an employee from a shift"));
        System.out.println(print_blue("3. Add a job certification to an employee"));
        System.out.println(print_blue("4. Remove a job certification from an employee"));
        System.out.println(print_blue("5. Assign an employee to a store"));
        System.out.println(print_blue("6. Remove an employee from a store"));
        System.out.println(print_blue("7. Add an employee"));
        System.out.println(print_blue("8. Remove an existing employee"));
        System.out.println(print_blue("9. Create a new store"));
        System.out.println(print_blue("10. Remove an existing store"));
        System.out.println(print_blue("11. Confirm a shift"));
        System.out.println(print_blue("12. Create a store's weekly schedule"));
        System.out.println(print_blue("13. Limit an employee's work"));
        System.out.println(print_blue("14. Remove an employee's work limitation"));
        System.out.println(print_blue("15. Confirm an employee's monthly salary"));
        System.out.println(print_blue("16. Change an employee's info"));
        System.out.println(print_blue("17. Show employees list"));
        System.out.println(print_blue("18. Show employee's info"));
        System.out.println(print_blue("19. Logout"));
        System.out.println(print_blue("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
        System.out.print(print_blue("Option:"));
    }

    public void add_hr(int id, String name, int bank_account, double salary, String terms_of_employment, Date employment_date, String family_status, boolean is_student, String password) {
        service.add_hr(id, name, bank_account, salary, terms_of_employment, employment_date, family_status, is_student, password);
    }
}
