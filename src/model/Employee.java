package model;

public class Employee {
    private int id;
    private int departmentId;
    private String fullName;
    private int age;
    private double salary;

    public Employee(int id, int departmentId, String fullName, int age, double salary) {
        this.id = id;
        this.departmentId = departmentId;
        this.fullName = fullName;
        this.age = age;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }
}
