package model;

/**
 * Represents the type of salary adjustment.
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
