package view;

import model.Department;

import java.util.List;

public class DepartmentView {
    public static void displayDepartments(List<Department> departments) {
        System.out.println("Список отделов:");
        for (Department department : departments) {
            System.out.println("ID: " + department.getId() + ", Название: " + department.getName() +
                    ", Кол-во сотрудников: " + department.getEmployeeCount());
        }
    }
}
