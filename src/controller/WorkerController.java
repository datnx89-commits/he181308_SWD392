package controller;

import model.IWorkerManagement;
import model.SalaryStatus;
import model.Worker;
import view.WorkerView;

/**
 * Controller of the program: receives the user's choice from the View,
 * calls the Model and asks the View to display the result.
 * DIP: depends on the IWorkerManagement abstraction, not on Management.
 */
public class WorkerController {

    private final IWorkerManagement management;
    private final WorkerView view;

    public WorkerController(IWorkerManagement management, WorkerView view) {
        this.management = management;
        this.view = view;
    }

    /** Main loop of the program. */
    public void run() {
        boolean running = true;
        while (running) {
            int choice = view.displayMenu();
            switch (choice) {
                case 1:
                    handleAddWorker();
                    break;
                case 2:
                    handleChangeSalary(SalaryStatus.INCREASE);
                    break;
                case 3:
                    handleChangeSalary(SalaryStatus.DECREASE);
                    break;
                case 4:
                    handleShowSalaryInfo();
                    break;
                case 5:
                    view.displayMessage("Goodbye!");
                    running = false;
                    break;
                default:
                    view.displayMessage("Invalid option, please select 1-5.");
            }
        }
    }

    /** Option 1: add a worker. */
    private void handleAddWorker() {
        try {
            Worker worker = view.inputWorker();
            if (management.addWorker(worker)) {
                view.displayMessage("Add worker successfully!");
            }
        } catch (Exception e) {
            view.displayMessage("ERROR: " + e.getMessage());
        }
    }

    /** Option 2 &amp; 3: increase or decrease salary. */
    private void handleChangeSalary(SalaryStatus status) {
        try {
            String code = view.inputCode();
            double amount = view.inputAmount();
            if (management.changeSalary(status, code, amount)) {
                view.displayMessage("Salary adjusted successfully!");
            }
        } catch (Exception e) {
            view.displayMessage("ERROR: " + e.getMessage());
        }
    }

    /** Option 4: show all workers that have been adjusted salary. */
    private void handleShowSalaryInfo() {
        view.displaySalaryHistories(management.getInfomationSalary());
    }
}
