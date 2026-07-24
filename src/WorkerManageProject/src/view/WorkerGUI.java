package view;

import controller.WorkerController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.SalaryHistory;
import model.SalaryStatus;
import model.Worker;
import observer.WorkerObserver;
import utils.ValidationUtils;

/**
 * View trong MVC (Java Swing). WorkerGUI chính là Concrete Observer:
 * Model thay đổi -> tự động nhận thông báo và cập nhật 2 bảng dữ liệu.
 * Validation dữ liệu đầu vào được thực hiện tại View (qua ValidationUtils)
 * trước khi ủy quyền xử lý cho Controller.
 */
public class WorkerGUI extends JFrame implements WorkerObserver {

    private WorkerController controller;

    // ---- Form thêm worker ----
    private JTextField txtCode;
    private JTextField txtName;
    private JTextField txtAge;
    private JTextField txtSalary;
    private JTextField txtLocation;

    // ---- Điều chỉnh lương ----
    private JTextField txtAdjustCode;
    private JTextField txtAmount;

    // ---- Bảng dữ liệu ----
    private DefaultTableModel workerTableModel;
    private DefaultTableModel historyTableModel;
    private JTable workerTable;

    /** Constructor mặc định — giữ để tương thích công cụ thiết kế. */
    public WorkerGUI() {
        initComponents();
    }

    /**
     * Constructor chính — nhận Controller được tiêm từ Main.java
     * (Dependency Injection).
     */
    public WorkerGUI(WorkerController controller) {
        this.controller = controller;
        initComponents();
        // Hiển thị dữ liệu ban đầu
        onWorkerListChanged(controller.getWorkers());
        onSalaryHistoryChanged(controller.getInfomationSalary());
    }

    // ================= Observer callbacks =================

    @Override
    public void onWorkerListChanged(List<Worker> workers) {
        workerTableModel.setRowCount(0);
        for (Worker w : workers) {
            workerTableModel.addRow(new Object[]{
                w.getId(), w.getName(), w.getAge(),
                String.format("%.2f", w.getSalary()), w.getWorkLocation()
            });
        }
    }

    @Override
    public void onSalaryHistoryChanged(List<SalaryHistory> histories) {
        historyTableModel.setRowCount(0);
        for (SalaryHistory h : histories) {
            historyTableModel.addRow(new Object[]{
                h.getWorkerId(), h.getWorkerName(),
                String.format("%.2f", h.getSalaryBefore()),
                String.format("%.2f", h.getAmount()),
                h.getStatus().getLabel(),
                String.format("%.2f", h.getSalaryAfter()),
                h.getAdjustedAtText()
            });
        }
    }

    // ================= UI =================

    private void initComponents() {
        setTitle("Worker Management System - J1.S.P0056 (MVC + Observer)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("WORKER MANAGEMENT SYSTEM", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        add(title, BorderLayout.NORTH);

        add(buildLeftPanel(), BorderLayout.WEST);
        add(buildTablesPanel(), BorderLayout.CENTER);

        setSize(1050, 640);
        setLocationRelativeTo(null);
    }

    /** Panel bên trái: form thêm worker + panel điều chỉnh lương. */
    private JPanel buildLeftPanel() {
        JPanel left = new JPanel();
        left.setLayout(new javax.swing.BoxLayout(left, javax.swing.BoxLayout.Y_AXIS));
        left.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 5));

        // ----- Form thêm worker -----
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Add a Worker"));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4, 4, 4, 4);
        gc.anchor = GridBagConstraints.WEST;
        gc.fill = GridBagConstraints.HORIZONTAL;

        txtCode = new JTextField(12);
        txtName = new JTextField(12);
        txtAge = new JTextField(12);
        txtSalary = new JTextField(12);
        txtLocation = new JTextField(12);

        String[] labels = {"Worker Code:", "Name:", "Age (18-50):", "Salary (> 0):", "Work Location:"};
        JTextField[] fields = {txtCode, txtName, txtAge, txtSalary, txtLocation};
        for (int i = 0; i < labels.length; i++) {
            gc.gridx = 0; gc.gridy = i; gc.weightx = 0;
            form.add(new JLabel(labels[i]), gc);
            gc.gridx = 1; gc.weightx = 1;
            form.add(fields[i], gc);
        }

        JButton btnAdd = new JButton("Add Worker");
        btnAdd.setBackground(new Color(0, 153, 51));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.addActionListener(evt -> onAddWorker());

        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(evt -> clearForm());

        JPanel formButtons = new JPanel(new GridLayout(1, 2, 6, 0));
        formButtons.add(btnAdd);
        formButtons.add(btnClear);
        gc.gridx = 0; gc.gridy = labels.length; gc.gridwidth = 2;
        form.add(formButtons, gc);

        // ----- Panel điều chỉnh lương -----
        JPanel adjust = new JPanel(new GridBagLayout());
        adjust.setBorder(BorderFactory.createTitledBorder("Adjust Salary"));
        GridBagConstraints ac = new GridBagConstraints();
        ac.insets = new Insets(4, 4, 4, 4);
        ac.anchor = GridBagConstraints.WEST;
        ac.fill = GridBagConstraints.HORIZONTAL;

        txtAdjustCode = new JTextField(12);
        txtAmount = new JTextField(12);

        ac.gridx = 0; ac.gridy = 0; ac.weightx = 0;
        adjust.add(new JLabel("Worker Code:"), ac);
        ac.gridx = 1; ac.weightx = 1;
        adjust.add(txtAdjustCode, ac);
        ac.gridx = 0; ac.gridy = 1; ac.weightx = 0;
        adjust.add(new JLabel("Amount (> 0):"), ac);
        ac.gridx = 1; ac.weightx = 1;
        adjust.add(txtAmount, ac);

        JButton btnIncrease = new JButton("Increase");
        btnIncrease.setBackground(new Color(0, 102, 204));
        btnIncrease.setForeground(Color.WHITE);
        btnIncrease.addActionListener(evt -> onChangeSalary(SalaryStatus.INCREASE));

        JButton btnDecrease = new JButton("Decrease");
        btnDecrease.setBackground(new Color(204, 51, 0));
        btnDecrease.setForeground(Color.WHITE);
        btnDecrease.addActionListener(evt -> onChangeSalary(SalaryStatus.DECREASE));

        JPanel adjustButtons = new JPanel(new GridLayout(1, 2, 6, 0));
        adjustButtons.add(btnIncrease);
        adjustButtons.add(btnDecrease);
        ac.gridx = 0; ac.gridy = 2; ac.gridwidth = 2;
        adjust.add(adjustButtons, ac);

        // ----- Exit -----
        JButton btnExit = new JButton("Exit");
        btnExit.setBackground(new Color(102, 102, 102));
        btnExit.setForeground(Color.WHITE);
        btnExit.addActionListener(evt -> System.exit(0));

        JPanel exitPanel = new JPanel(new GridLayout(1, 1));
        exitPanel.setBorder(BorderFactory.createEmptyBorder(8, 4, 0, 4));
        exitPanel.add(btnExit);

        left.add(form);
        left.add(javax.swing.Box.createVerticalStrut(8));
        left.add(adjust);
        left.add(javax.swing.Box.createVerticalStrut(8));
        left.add(exitPanel);
        left.add(javax.swing.Box.createVerticalGlue());
        return left;
    }

    /** Panel giữa: bảng worker + bảng lịch sử lương. */
    private JSplitPane buildTablesPanel() {
        workerTableModel = new DefaultTableModel(
                new String[]{"Code", "Name", "Age", "Salary", "Work Location"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        workerTable = new JTable(workerTableModel);
        workerTable.getSelectionModel().addListSelectionListener(e -> {
            int row = workerTable.getSelectedRow();
            if (row >= 0) {
                txtAdjustCode.setText(String.valueOf(workerTableModel.getValueAt(row, 0)));
            }
        });
        JScrollPane workerScroll = new JScrollPane(workerTable);
        workerScroll.setBorder(BorderFactory.createTitledBorder("Workers"));

        historyTableModel = new DefaultTableModel(
                new String[]{"Code", "Name", "Before", "Amount", "Status", "After", "Time"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable historyTable = new JTable(historyTableModel);
        JScrollPane historyScroll = new JScrollPane(historyTable);
        historyScroll.setBorder(BorderFactory.createTitledBorder(
                "Adjusted Salary Workers (sorted by code)"));

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, workerScroll, historyScroll);
        split.setResizeWeight(0.5);
        split.setPreferredSize(new Dimension(760, 520));
        split.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 10));
        return split;
    }

    // ================= Actions =================

    /** Xử lý nút Add Worker: validate tại View rồi ủy quyền cho Controller. */
    private void onAddWorker() {
        String code = txtCode.getText().trim();
        String name = txtName.getText().trim();
        String ageStr = txtAge.getText().trim();
        String salaryStr = txtSalary.getText().trim();
        String location = txtLocation.getText().trim();

        if (!ValidationUtils.isNotEmpty(code, name, ageStr, salaryStr, location)) {
            showError("Please fill in all fields.");
            return;
        }
        if (!ValidationUtils.isValidInt(ageStr)) {
            showError("Age must be an integer number.");
            return;
        }
        int age = Integer.parseInt(ageStr);
        if (!ValidationUtils.isValidAge(age)) {
            showError("Age must be in range 18 to 50.");
            return;
        }
        if (!ValidationUtils.isValidDouble(salaryStr)) {
            showError("Salary must be a number.");
            return;
        }
        double salary = Double.parseDouble(salaryStr);
        if (!ValidationUtils.isPositive(salary)) {
            showError("Salary must be greater than 0.");
            return;
        }
        if (controller.isCodeExisted(code)) {
            showError("Worker code [" + code + "] is duplicated in DB.");
            return;
        }
        try {
            controller.addWorker(new Worker(code, name, age, salary, location));
            JOptionPane.showMessageDialog(this, "Add worker successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    /** Xử lý nút Increase / Decrease: validate rồi ủy quyền cho Controller. */
    private void onChangeSalary(SalaryStatus status) {
        String code = txtAdjustCode.getText().trim();
        String amountStr = txtAmount.getText().trim();

        if (!ValidationUtils.isNotEmpty(code, amountStr)) {
            showError("Please enter worker code and amount.");
            return;
        }
        if (!ValidationUtils.isValidDouble(amountStr)) {
            showError("Amount must be a number.");
            return;
        }
        double amount = Double.parseDouble(amountStr);
        if (!ValidationUtils.isPositive(amount)) {
            showError("Amount of money must be greater than 0.");
            return;
        }
        try {
            controller.changeSalary(status, code, amount);
            JOptionPane.showMessageDialog(this, "Salary adjusted successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            txtAmount.setText("");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void clearForm() {
        txtCode.setText("");
        txtName.setText("");
        txtAge.setText("");
        txtSalary.setText("");
        txtLocation.setText("");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
