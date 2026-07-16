import controller.WorkerController;
import model.Management;
import view.WorkerView;

/**
 * LAB211 - J1.S.P0056: Program to manage worker information.
 * Architecture: MVC pattern.
 * Entry point: wires Model, View and Controller together.
 */
public class Main {

    public static void main(String[] args) {
        WorkerController controller = new WorkerController(new Management(), new WorkerView());
        controller.run();
    }
}
