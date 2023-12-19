import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import model.DatabaseManager;
import model.Department;
import model.Employee;
import controller.DepartmentController;
import controller.EmployeeController;
import controller.EnterpriseManagementController;
import view.EnterpriseManagementSystemView;

import java.util.Scanner;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.List;

public class EnterpriseManagementSystem {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new MainHandler());
        server.createContext("/departments", new DepartmentHandler());
        server.createContext("/employees", new EmployeeHandler());
        server.setExecutor(null);
        server.start();

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

    static class MainHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "Welcome to Enterprise Management System!";
            sendResponse(t, response);
        }
    }

    static class DepartmentHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String method = t.getRequestMethod();

            switch (method) {
                case "GET":
                    handleGetDepartments(t);
                    break;
                default:
                    sendResponse(t, "Unsupported method");
            }
        }

        private void handleGetDepartments(HttpExchange t) throws IOException {
            List<Department> departments = DatabaseManager.getAllDepartments();
            StringBuilder response = new StringBuilder();
            response.append("<html><body><h1>Departments</h1><ul>");

            for (Department department : departments) {
                response.append("<li>").append(department.getName()).append("</li>");
            }

            response.append("</ul></body></html>");
            sendHtmlResponse(t, response.toString());
        }
    }

    static class EmployeeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String method = t.getRequestMethod();

            switch (method) {
                case "GET":
                    handleGetEmployees(t);
                    break;
                default:
                    sendResponse(t, "Unsupported method");
            }
        }

        private void handleGetEmployees(HttpExchange t) throws IOException {
            List<Employee> employees = DatabaseManager.getAllEmployees();
            StringBuilder response = new StringBuilder();
            response.append("<html><body><h1>Employees</h1><ul>");

            for (Employee employee : employees) {
                response.append("<li>").append(employee.getFullName()).append("</li>");
            }

            response.append("</ul></body></html>");
            sendHtmlResponse(t, response.toString());
        }
    }

    private static void sendResponse(HttpExchange t, String response) throws IOException {
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static void sendHtmlResponse(HttpExchange t, String response) throws IOException {
        t.getResponseHeaders().add("Content-Type", "text/html");
        sendResponse(t, response);
    }
}

