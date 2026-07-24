package controller;

import java.util.List;
import model.SalaryHistory;
import model.SalaryStatus;
import model.Worker;
import model.WorkerListModel;
import observer.WorkerObserver;

/**
 * Controller trong MVC. Lớp này nhận yêu cầu từ View và ủy quyền
 * xử lý dữ liệu cho Model (WorkerListModel).
 * DIP: View chỉ làm việc với Controller, không đụng trực tiếp vào Model.
 */
public class WorkerController {

    private final WorkerListModel workerListModel;

    public WorkerController() {
        this.workerListModel = new WorkerListModel();
    }

    public WorkerController(WorkerListModel workerListModel) {
        this.workerListModel = workerListModel;
    }

    // ================= Observer =================

    public void addObserver(WorkerObserver observer) {
        workerListModel.addObserver(observer);
    }

    public void removeObserver(WorkerObserver observer) {
        workerListModel.removeObserver(observer);
    }

    // ================= Use cases =================

    /** Nạp dữ liệu mẫu vào Model. */
    public void getSampleData() {
        try {
            workerListModel.addWorker(new Worker("W01", "Tran Thi A", 30, 8000, "Da Nang"));
            workerListModel.addWorker(new Worker("W02", "Nguyen Van B", 25, 5000, "Ha Noi"));
        } catch (Exception ex) {
            // dữ liệu mẫu hợp lệ nên không xảy ra; log để an toàn
            System.err.println("Sample data error: " + ex.getMessage());
        }
    }

    /** Option 1: thêm worker. */
    public boolean addWorker(Worker worker) throws Exception {
        return workerListModel.addWorker(worker);
    }

    /** Option 2 & 3: tăng / giảm lương. */
    public boolean changeSalary(SalaryStatus status, String code, double amount) throws Exception {
        return workerListModel.changeSalary(status, code, amount);
    }

    /** Option 4: danh sách lịch sử điều chỉnh lương (sort theo id). */
    public List<SalaryHistory> getInfomationSalary() {
        return workerListModel.getInfomationSalary();
    }

    public List<Worker> getWorkers() {
        return workerListModel.getWorkers();
    }

    public Worker getWorker(String code) {
        return workerListModel.getWorker(code);
    }

    public boolean isCodeExisted(String code) {
        return workerListModel.isCodeExisted(code);
    }
}
