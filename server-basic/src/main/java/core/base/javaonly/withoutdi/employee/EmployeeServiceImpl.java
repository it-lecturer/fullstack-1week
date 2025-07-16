package core.base.javaonly.withoutdi.employee;

import com.zaxxer.hikari.HikariDataSource;
import core.base.javaonly.connection.ConnectionConst;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import static core.base.javaonly.connection.ConnectionConst.*;

public class EmployeeServiceImpl implements EmployeeService {
    private DataSource dataSource;
    private EmployeeRepository employeeRepository;

    @Override
    public void register(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public Employee findEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }
}
