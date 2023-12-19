package controller;

import model.DatabaseManager;
import model.Department;
import view.DepartmentView;

import java.util.List;

public class DepartmentController {
    public static void addDepartment(String name) {
        DatabaseManager.addDepartment(name);
    }

    public static void deleteDepartment(int departmentId) {
        DatabaseManager.deleteDepartment(departmentId);
    }

    public static void showAllDepartments() {
        List<Department> departments = DatabaseManager.getAllDepartments();
        DepartmentView.displayDepartments(departments);
    }
}
