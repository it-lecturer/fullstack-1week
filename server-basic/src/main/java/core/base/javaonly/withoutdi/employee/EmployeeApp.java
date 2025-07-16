package core.base.javaonly.withoutdi.employee;

import core.base.javaonly.connection.ConnectionConst;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import static core.base.javaonly.connection.ConnectionConst.*;

public class EmployeeApp {
    public static void main(String[] args) {
        DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        EmployeeService employeeService = new EmployeeServiceImpl(
//                new MemoryEmployeeRepository()
                new JDBCEmployeeRepository(dataSource)
        );
        Employee employee = new Employee(1L, "김상헌", JobLevel.Assistant, 50000);

        employeeService.register(employee);

        Employee findEmployee = employeeService.findEmployee(1L);

        System.out.println("new employee = " + employee);
        System.out.println("find employee = " + findEmployee);
    }
}
