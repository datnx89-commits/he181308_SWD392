package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Business logic (Model) of the program: add worker, adjust salary,
 * get salary adjustment history.
 * SRP: contains only business rules, no console input/output.
 */
public class Management implements IWorkerManagement {

    public static final int MIN_AGE = 18;
    public static final int MAX_AGE = 50;

    /** In-memory "DB" of workers, keyed by worker code (id). */
    private final Map<String, Worker> workerDB = new LinkedHashMap<>();

    /** Salary adjustment history. */
    private final List<SalaryHistory> salaryHistories = new ArrayList<>();

    /**
     * Option 1: Add a worker after validating its data.
     *
     * @param worker worker information
     * @return worker added status
     * @throws Exception when data is invalid
     */
    @Override
    public boolean addWorker(Worker worker) throws Exception {
        validateWorker(worker);
        workerDB.put(worker.getId(), worker);
        return true;
    }

    /**
     * Option 2 &amp; 3: Increase or decrease salary of a worker
     * and save the salary history.
     *
     * @param status increase or decrease
     * @param code   worker code (id)
     * @param amount amount of money
     * @return status of adjustment
     * @throws Exception when data is invalid
     */
    @Override
    public boolean changeSalary(SalaryStatus status, String code, double amount) throws Exception {
        Worker worker = workerDB.get(code);
        if (worker == null) {
            throw new Exception("Worker code [" + code + "] does not exist in DB.");
        }
        if (amount <= 0) {
            throw new Exception("Amount of money must be greater than 0.");
        }
        double salaryBefore = worker.getSalary();
        double salaryAfter = (status == SalaryStatus.INCREASE)
                ? salaryBefore + amount
                : salaryBefore - amount;
        if (salaryAfter <= 0) {
            throw new Exception("Salary after adjustment must be greater than 0.");
        }
        worker.setSalary(salaryAfter);
        salaryHistories.add(new SalaryHistory(worker.getId(), worker.getName(),
                salaryBefore, amount, status, salaryAfter, LocalDateTime.now()));
        return true;
    }

    /**
     * Option 4: Get the list of salary adjustments, sorted by worker code (id).
     *
     * @return list of salary histories sorted by worker id
     */
    @Override
    public List<SalaryHistory> getInfomationSalary() {
        List<SalaryHistory> result = new ArrayList<>(salaryHistories);
        result.sort(Comparator.comparing(SalaryHistory::getWorkerId));
        return result;
    }

    /** Validates worker data according to the assignment specification. */
    private void validateWorker(Worker worker) throws Exception {
        if (worker.getId() == null || worker.getId().trim().isEmpty()) {
            throw new Exception("Worker code (id) cannot be null or empty.");
        }
        if (workerDB.containsKey(worker.getId())) {
            throw new Exception("Worker code [" + worker.getId() + "] is duplicated in DB.");
        }
        if (worker.getAge() < MIN_AGE || worker.getAge() > MAX_AGE) {
            throw new Exception("Age must be in range " + MIN_AGE + " to " + MAX_AGE + ".");
        }
        if (worker.getSalary() <= 0) {
            throw new Exception("Salary must be greater than 0.");
        }
    }
}
