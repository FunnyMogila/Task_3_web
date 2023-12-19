<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Departments</title>
</head>
<body>
<h2>Departments</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Number of Employees</th>
    </tr>
    <c:forEach var="department" items="${departments}">
        <tr>
            <td>${department.id}</td>
            <td>${department.name}</td>
            <td>${department.numberOfEmployees}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

