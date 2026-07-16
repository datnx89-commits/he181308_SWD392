package view;

import java.util.List;
import java.util.Scanner;
import model.SalaryHistory;
import model.Worker;

/**
 * View of the program: all console input/output is placed here.
 * SRP: no business logic, only interaction with the user.
 */
public class WorkerView {

    private final Scanner scanner = new Scanner(System.in);

    /** Displays the main menu and returns the selected option. */
    public int displayMenu() {
        System.out.println();
        System.out.println("===== WORKER MANAGEMENT =====");
        System.out.println("1. Add a worker");
        System.out.println("2. Increase salary");
        System.out.println("3. Decrease salary");
        System.out.println("4. Show adjusted salary worker information");
        System.out.println("5. Quit");
        return inputInt("Please select an option (1-5): ");
    }

    /** Prompts the user to input full worker information. */
    public Worker inputWorker() {
        String id = inputString("Enter worker code (id): ");
        String name = inputString("Enter worker name: ");
        int age = inputInt("Enter worker age: ");
        double salary = inputDouble("Enter worker salary: ");
        String workLocation = inputString("Enter work location: ");
        return new Worker(id, name, age, salary, workLocation);
    }

    /** Prompts the user to input a worker code. */
    public String inputCode() {
        return inputString("Enter worker code (id): ");
    }

    /** Prompts the user to input an amount of money. */
    public double inputAmount() {
        return inputDouble("Enter amount of money: ");
    }

    /** Displays the list of salary adjustment records as a table. */
    public void displaySalaryHistories(List<SalaryHistory> histories) {
        if (histories.isEmpty()) {
            System.out.println("No worker has been adjusted salary.");
            return;
        }
        System.out.println("----- ADJUSTED SALARY WORKERS -----");
        System.out.printf("%-8s | %-20s | %12s | %12s | %-6s | %12s | %-19s%n",
                "Code", "Name", "Before", "Amount", "Status", "After", "Time");
        for (SalaryHistory h : histories) {
            System.out.printf("%-8s | %-20s | %12.2f | %12.2f | %-6s | %12.2f | %-19s%n",
                    h.getWorkerId(), h.getWorkerName(), h.getSalaryBefore(),
                    h.getAmount(), h.getStatus().getLabel(), h.getSalaryAfter(),
                    h.getAdjustedAtText());
        }
    }

    /** Displays a message to the user. */
    public void displayMessage(String msg) {
        System.out.println(msg);
    }

    private String inputString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int inputInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(inputString(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, please try again.");
            }
        }
    }

    private double inputDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(inputString(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, please try again.");
            }
        }
    }
}
