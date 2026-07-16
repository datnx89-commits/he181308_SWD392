package model;

import java.util.List;

/**
 * Abstraction of the worker management business logic.
 * DIP: the controller depends on this interface, not on the concrete class.
 * OCP: a new implementation (e.g. file/database storage) can be added
 * without modifying the controller.
 */
public interface IWorkerManagement {

    boolean addWorker(Worker worker) throws Exception;

    boolean changeSalary(SalaryStatus status, String code, double amount) throws Exception;

    List<SalaryHistory> getInfomationSalary();
}
