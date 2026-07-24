package main;

import controller.WorkerController;
import view.WorkerGUI;

/**
 * Điểm khởi chạy của ứng dụng (Bootstrap Layer).
 *
 * Nhiệm vụ duy nhất của lớp này:
 *   1. Khởi tạo Controller (chứa dữ liệu in-memory)
 *   2. Nạp dữ liệu mẫu vào Controller
 *   3. Khởi tạo View và tiêm Controller vào (Dependency Injection)
 *   4. Đăng ký View làm Observer của Model
 *   5. Hiển thị giao diện
 *
 * Không chứa bất kỳ business logic nào.
 */
public class Main {

    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel(
                    javax.swing.UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> {
            // B1: Khởi tạo Controller
            WorkerController controller = new WorkerController();

            // B2: Nạp dữ liệu mẫu
            controller.getSampleData();

            // B3: Tạo View (tiêm Controller — Dependency Injection)
            WorkerGUI view = new WorkerGUI(controller);

            // B4: Đăng ký Observer — View tự cập nhật khi Model thay đổi
            controller.addObserver(view);

            // B5: Hiển thị
            view.setVisible(true);
        });
    }
}
