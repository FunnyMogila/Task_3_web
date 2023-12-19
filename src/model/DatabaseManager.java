package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/enterprise";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12cc14sd";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    public static void createTablesIfNotExist() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, "root", "12cc14sd");
             Statement statement = connection.createStatement()) {

            // Создание базы данных, если ее нет
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS enterprise");

            // Использование базы данных
            statement.executeUpdate("USE enterprise");

            // Создание таблицы отделов
            createDepartmentsTable(connection);

            // Создание таблицы сотрудников
            createEmployeesTable(connection);

            System.out.println("Таблицы и база данных успешно созданы или уже существуют.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createDepartmentsTable(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS departments (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT," +
                        "name VARCHAR(255) NOT NULL," +
                        "employee_count INT DEFAULT 0)"
        )) {
            preparedStatement.executeUpdate();
        }
    }

    private static void createEmployeesTable(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS employees (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT," +
                        "department_id INT," +
                        "full_name VARCHAR(255) NOT NULL," +
                        "age INT," +
                        "salary DOUBLE," +
                        "FOREIGN KEY (department_id) REFERENCES departments(id))"
        )) {
            preparedStatement.executeUpdate();
        }
    }

    private static boolean doesTableExist(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        try (ResultSet resultSet = metaData.getTables(null, null, tableName, null)) {
            return resultSet.next();
        }
    }

    public static void addDepartment(String name) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO departments (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int departmentId = generatedKeys.getInt(1);
                    System.out.println("Отдел успешно добавлен (ID: " + departmentId + ")");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteDepartment(int departmentId) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM departments WHERE id = ?")) {
            statement.setInt(1, departmentId);
            statement.executeUpdate();
            System.out.println("Отдел успешно удален");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM departments")) {
            while (resultSet.next()) {
                Department department = new Department(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getInt("employee_count"));
                departments.add(department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }

    public static void addEmployee(int departmentId, String fullName, int age, double salary) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO employees (department_id, full_name, age, salary) VALUES (?, ?, ?, ?)")) {
            statement.setInt(1, departmentId);
            statement.setString(2, fullName);
            statement.setInt(3, age);
            statement.setDouble(4, salary);
            statement.executeUpdate();

            updateEmployeeCount(connection, departmentId, 1);

            System.out.println("Сотрудник успешно добавлен");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteEmployee(int employeeId) {
        int departmentId = getDepartmentIdByEmployee(employeeId);

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM employees WHERE id = ?")) {
            statement.setInt(1, employeeId);
            statement.executeUpdate();

            updateEmployeeCount(connection, departmentId, -1);

            System.out.println("Сотрудник успешно удален");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Employee> getEmployeesInDepartment(int departmentId) {
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM employees WHERE department_id = ?")) {
            statement.setInt(1, departmentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Employee employee = new Employee(resultSet.getInt("id"), resultSet.getInt("department_id"),
                            resultSet.getString("full_name"), resultSet.getInt("age"), resultSet.getDouble("salary"));
                    employees.add(employee);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public static double getTotalSalaryInDepartment(int departmentId) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT SUM(salary) as total_salary FROM employees WHERE department_id = ?")) {
            statement.setInt(1, departmentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("total_salary");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    private static void updateEmployeeCount(Connection connection, int departmentId, int countChange) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE departments SET employee_count = employee_count + ? WHERE id = ?")) {
            statement.setInt(1, countChange);
            statement.setInt(2, departmentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int getDepartmentIdByEmployee(int employeeId) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT department_id FROM employees WHERE id = ?")) {
            statement.setInt(1, employeeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("department_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM employees");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int departmentId = resultSet.getInt("department_id");
                String fullName = resultSet.getString("full_name");
                int age = resultSet.getInt("age");
                double salary = resultSet.getDouble("salary");

                employees.add(new Employee(id, departmentId, fullName, age, salary));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
}

