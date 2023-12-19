package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/enterprise";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12cc14sd";

    public static List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM departments");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int employeeCount = resultSet.getInt("employee_count");

                departments.add(new Department(id, name, employeeCount));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }
}
