package observer;

import java.util.List;
import model.SalaryHistory;
import model.Worker;

/**
 * Interface Observer trong Observer Pattern.
 * View implement interface này để được Model tự động thông báo
 * mỗi khi danh sách worker hoặc lịch sử lương thay đổi.
 */
public interface WorkerObserver {

    void onWorkerListChanged(List<Worker> workers);

    void onSalaryHistoryChanged(List<SalaryHistory> histories);
}
