package model;

/**
 * Entity class representing a Worker.
 * SRP: only holds worker data, no business logic.
 */
public class Worker {

    private final String id;
    private final String name;
    private final int age;
    private double salary;
    private final String workLocation;

    public Worker(String id, String name, int age, double salary, String workLocation) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.workLocation = workLocation;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getWorkLocation() {
        return workLocation;
    }
}
