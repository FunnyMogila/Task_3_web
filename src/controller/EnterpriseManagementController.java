package controller;

import view.EnterpriseManagementSystemView;

import java.util.Scanner;

public class EnterpriseManagementController {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            EnterpriseManagementSystemView.displayMenu();

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.println("Введите название отдела:");
                    String departmentName = scanner.nextLine();
                    DepartmentController.addDepartment(departmentName);
                    break;
                case 2:
                    System.out.println("Введите ID отдела для удаления:");
                    int departmentId = scanner.nextInt();
                    DepartmentController.deleteDepartment(departmentId);
                    break;
                case 3:
                    DepartmentController.showAllDepartments();
                    break;
                case 4:
                    System.out.println("Введите ID отдела:");
                    int empDepartmentId = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.println("Введите ФИО сотрудника:");
                    String empFullName = scanner.nextLine();
                    System.out.println("Введите возраст сотрудника:");
                    int empAge = scanner.nextInt();
                    System.out.println("Введите ЗП сотрудника:");
                    double empSalary = scanner.nextDouble();
                    EmployeeController.addEmployee(empDepartmentId, empFullName, empAge, empSalary);
                    break;
                case 5:
                    System.out.println("Введите ID сотрудника для удаления:");
                    int empId = scanner.nextInt();
                    EmployeeController.deleteEmployee(empId);
                    break;
                case 6:
                    System.out.println("Введите ID отдела:");
                    int showEmpDepartmentId = scanner.nextInt();
                    EmployeeController.showEmployeesInDepartment(showEmpDepartmentId);
                    break;
                case 7:
                    System.out.println("Введите ID отдела:");
                    int showSalaryDepartmentId = scanner.nextInt();
                    EmployeeController.showTotalSalaryInDepartment(showSalaryDepartmentId);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Некорректный ввод");
            }
        }
    }
}
