package core.base;

import core.base.javaonly.withdi.employee.*;
import core.base.javaonly.withdi.incentive.IncentivePolicy;
import core.base.javaonly.withdi.incentive.SalaryRatioIncentivePolicy;
import core.base.javaonly.withdi.payroll.JDBCPayrollRepository;
import core.base.javaonly.withdi.payroll.PayrollRepository;
import core.base.javaonly.withdi.payroll.PayrollService;
import core.base.javaonly.withdi.payroll.PayrollServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import static core.base.javaonly.connection.ConnectionConst.*;

public class JavaOnlyAppConfig {

    public DataSource dataSource() {
        return new DriverManagerDataSource(URL, USERNAME, PASSWORD);
    }

    public EmployeeRepository employeeRepository() {
        return new JDBCEmployeeRepository(dataSource());
    }

    public PayrollRepository payrollRepository() {
        return new JDBCPayrollRepository(dataSource());
    }

    public IncentivePolicy incentivePolicy() {
        return new SalaryRatioIncentivePolicy(JobLevel.Manager, 0.1);
    }

    public PayrollService payrollService() {
        return new PayrollServiceImpl(incentivePolicy(), employeeRepository(), payrollRepository());
    }

    public EmployeeService employeeService() {
        return new EmployeeServiceImpl(employeeRepository());
    }
}
