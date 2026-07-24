package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import observer.WorkerObserver;

/**
 * Model chính quản lý danh sách Worker và lịch sử điều chỉnh lương.
 * Lớp này đóng vai trò Subject trong Observer Pattern: mỗi khi dữ liệu
 * thay đổi (thêm worker, điều chỉnh lương) sẽ tự động notify các Observer.
 *
 * Chứa 3 method bắt buộc của đề J1.S.P0056:
 *   - addWorker(Worker): boolean
 *   - changeSalary(SalaryStatus, String, double): boolean
 *   - getInfomationSalary(): List&lt;SalaryHistory&gt;
 */
public class WorkerListModel {

    public static final int MIN_AGE = 18;
    public static final int MAX_AGE = 50;

    /** "DB" trong bộ nhớ, key là mã worker (id). */
    private final Map<String, Worker> workerDB;

    /** Lịch sử điều chỉnh lương. */
    private final List<SalaryHistory> salaryHistories;

    /** Danh sách Observer đã đăng ký. */
    private final List<WorkerObserver> observers;

    public WorkerListModel() {
        this.workerDB = new LinkedHashMap<>();
        this.salaryHistories = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    // ================= Observer Pattern =================

    public void addObserver(WorkerObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(WorkerObserver observer) {
        observers.remove(observer);
    }

    private void notifyWorkerListChanged() {
        List<Worker> readOnly = Collections.unmodifiableList(new ArrayList<>(workerDB.values()));
        for (WorkerObserver observer : observers) {
            observer.onWorkerListChanged(readOnly);
        }
    }

    private void notifySalaryHistoryChanged() {
        List<SalaryHistory> readOnly = Collections.unmodifiableList(getInfomationSalary());
        for (WorkerObserver observer : observers) {
            observer.onSalaryHistoryChanged(readOnly);
        }
    }

    // ================= Business logic =================

    /**
     * Thêm một worker sau khi kiểm tra business rule.
     *
     * @param worker thông tin worker
     * @return trạng thái thêm thành công
     * @throws Exception khi dữ liệu vi phạm business rule
     */
    public boolean addWorker(Worker worker) throws Exception {
        if (worker == null || worker.getId() == null || worker.getId().trim().isEmpty()) {
            throw new Exception("Worker code (id) cannot be null or empty.");
        }
        if (isCodeExisted(worker.getId())) {
            throw new Exception("Worker code [" + worker.getId() + "] is duplicated in DB.");
        }
        if (worker.getAge() < MIN_AGE || worker.getAge() > MAX_AGE) {
            throw new Exception("Age must be in range " + MIN_AGE + " to " + MAX_AGE + ".");
        }
        if (worker.getSalary() <= 0) {
            throw new Exception("Salary must be greater than 0.");
        }
        workerDB.put(worker.getId(), worker);
        notifyWorkerListChanged();
        return true;
    }

    /**
     * Tăng hoặc giảm lương của một worker và lưu lịch sử.
     *
     * @param status INCREASE hoặc DECREASE
     * @param code   mã worker
     * @param amount số tiền điều chỉnh
     * @return trạng thái điều chỉnh thành công
     * @throws Exception khi dữ liệu vi phạm business rule
     */
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
        notifyWorkerListChanged();
        notifySalaryHistoryChanged();
        return true;
    }

    /**
     * Trả về danh sách lịch sử điều chỉnh lương, sort theo mã worker (id).
     */
    public List<SalaryHistory> getInfomationSalary() {
        List<SalaryHistory> result = new ArrayList<>(salaryHistories);
        result.sort(Comparator.comparing(SalaryHistory::getWorkerId));
        return result;
    }

    /** Kiểm tra mã worker đã tồn tại trong DB chưa. */
    public boolean isCodeExisted(String code) {
        if (code == null) {
            return false;
        }
        for (String key : workerDB.keySet()) {
            if (key.equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }

    public Worker getWorker(String code) {
        return workerDB.get(code);
    }

    public List<Worker> getWorkers() {
        return new ArrayList<>(workerDB.values());
    }
}
