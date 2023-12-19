package view;

import model.Employee;

import java.util.List;

public class EmployeeView {
    public static void displayEmployees(List<Employee> employees) {
        System.out.println("Список сотрудников:");
        for (Employee employee : employees) {
            System.out.println("ID: " + employee.getId() + ", ФИО: " + employee.getFullName() +
                    ", Возраст: " + employee.getAge() + ", ЗП: " + employee.getSalary());
        }
    }
}
