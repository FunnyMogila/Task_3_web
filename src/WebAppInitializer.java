import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.Set;

public class WebAppInitializer implements javax.servlet.ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        ServletRegistration.Dynamic mainServlet = ctx.addServlet("MainServlet", new MainServlet());
        mainServlet.addMapping("/");

        ServletRegistration.Dynamic departmentServlet = ctx.addServlet("DepartmentServlet", new DepartmentServlet());
        departmentServlet.addMapping("/departments");

        ServletRegistration.Dynamic employeeServlet = ctx.addServlet("EmployeeServlet", new EmployeeServlet());
        employeeServlet.addMapping("/employees");
    }
}
