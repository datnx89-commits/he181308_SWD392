package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Entity lưu một lần điều chỉnh lương của Worker.
 * SRP: chỉ chứa dữ liệu lịch sử, không chứa business logic.
 */
public class SalaryHistory {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final String workerId;
    private final String workerName;
    private final double salaryBefore;
    private final double amount;
    private final SalaryStatus status;
    private final double salaryAfter;
    private final LocalDateTime adjustedAt;

    public SalaryHistory(String workerId, String workerName, double salaryBefore,
                         double amount, SalaryStatus status, double salaryAfter,
                         LocalDateTime adjustedAt) {
        this.workerId = workerId;
        this.workerName = workerName;
        this.salaryBefore = salaryBefore;
        this.amount = amount;
        this.status = status;
        this.salaryAfter = salaryAfter;
        this.adjustedAt = adjustedAt;
    }

    public String getWorkerId() {
        return workerId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public double getSalaryBefore() {
        return salaryBefore;
    }

    public double getAmount() {
        return amount;
    }

    public SalaryStatus getStatus() {
        return status;
    }

    public double getSalaryAfter() {
        return salaryAfter;
    }

    public LocalDateTime getAdjustedAt() {
        return adjustedAt;
    }

    public String getAdjustedAtText() {
        return adjustedAt.format(FORMATTER);
    }
}
