package model;

/**
 * Trạng thái điều chỉnh lương: tăng (UP) hoặc giảm (DOWN).
 */
public enum SalaryStatus {
    INCREASE("UP"),
    DECREASE("DOWN");

    private final String label;

    SalaryStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
