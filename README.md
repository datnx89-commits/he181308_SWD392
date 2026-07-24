# he181308_SWD392

Họ và tên: Nguyễn Xuân Đạt
- Mã sinh viên: HE181308

Môn học: SWD392 - Software Architecture and Design

Đề tài:
J1.S.P0056 - Program to manage worker information

Mô tả:
Dự án xây dựng ứng dụng quản lý thông tin worker trên nền tảng Java Desktop (Java Swing), áp dụng kiến trúc Model - View - Controller (MVC). Hệ thống đáp ứng đầy đủ các chức năng theo yêu cầu của đề J1.S.P0056.

Kiến trúc sử dụng:
- Model - View - Controller (MVC)
- Observer Pattern (Model tự động notify View khi dữ liệu thay đổi)

Cấu trúc project (`src/WorkerManageProject/src`):
- controller: Điều phối yêu cầu từ View đến Model, xử lý nghiệp vụ (`WorkerController`).
- model: Quản lý dữ liệu worker và lịch sử lương (`Worker`, `SalaryHistory`, `SalaryStatus`, `WorkerListModel` — Subject của Observer Pattern).
- observer: Chứa interface `WorkerObserver` phục vụ Observer Pattern.
- view: Giao diện người dùng Java Swing (`WorkerGUI` — Concrete Observer), hiển thị và kiểm tra dữ liệu đầu vào.
- utils: Chứa các hàm tiện ích static kiểm tra dữ liệu đầu vào (`ValidationUtils`).
- main: Khởi tạo chương trình, kết nối các thành phần của hệ thống (Dependency Injection).

Nguyên tắc thiết kế:
- Áp dụng mô hình MVC để tách biệt giao diện, xử lý và dữ liệu.
- Áp dụng Observer Pattern để View tự động cập nhật khi Model thay đổi.
- Tuân thủ các nguyên tắc SOLID (Single Responsibility, Dependency Injection, Separation of Concerns và Low Coupling).

Chức năng (theo đề J1.S.P0056):
- Thêm worker (validate: code không rỗng/không trùng, tuổi 18–50, lương > 0).
- Tăng lương worker (code phải tồn tại, số tiền > 0, lưu lịch sử).
- Giảm lương worker (code phải tồn tại, số tiền > 0, lương sau giảm > 0, lưu lịch sử).
- Hiển thị danh sách worker đã điều chỉnh lương (sort theo mã worker).
- Validate dữ liệu đầu vào trước khi xử lý.

3 method bắt buộc của đề nằm trong `model/WorkerListModel.java`:
- `public boolean addWorker(Worker worker) throws Exception`
- `public boolean changeSalary(SalaryStatus status, String code, double amount) throws Exception`
- `public List<SalaryHistory> getInfomationSalary()`

Sơ đồ thiết kế: (XEM TRONG THƯ MỤC "docs" và "diagrams")
- Use Case Diagram, Activity Diagram.
- MVC Architecture Diagram, Package Diagram.
- Class Diagram, Sequence Diagram.

Cách chạy:
- Mở project `src/WorkerManageProject` bằng NetBeans → Run (F6), hoặc:

```bash
cd src/WorkerManageProject
javac -encoding UTF-8 -d out src/*/*.java
java -cp out main.Main
```

Ghi chú:
- Project được phát triển bằng Java Swing trên NetBeans.
- Source code đã được tổ chức theo kiến trúc MVC.
- Observer Pattern được sử dụng để Model tự động notify View.
- Validation được thực hiện tại View (qua `utils/ValidationUtils`) trước khi chuyển yêu cầu đến Controller; business rule được kiểm tra lần cuối tại Model.
