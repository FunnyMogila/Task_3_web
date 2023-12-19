package controller;

import model.DatabaseManager;
import model.Employee;
import view.EmployeeView;

import java.util.List;

public class EmployeeController {
    public static void addEmployee(int departmentId, String fullName, int age, double salary) {
        DatabaseManager.addEmployee(departmentId, fullName, age, salary);
    }

    public static void deleteEmployee(int employeeId) {
        DatabaseManager.deleteEmployee(employeeId);
    }

    public static void showEmployeesInDepartment(int departmentId) {
        List<Employee> employees = DatabaseManager.getEmployeesInDepartment(departmentId);
        EmployeeView.displayEmployees(employees);
    }

    public static void showTotalSalaryInDepartment(int departmentId) {
        double totalSalary = DatabaseManager.getTotalSalaryInDepartment(departmentId);
        System.out.println("Сумма ЗП в отделе: " + totalSalary);
    }
}
