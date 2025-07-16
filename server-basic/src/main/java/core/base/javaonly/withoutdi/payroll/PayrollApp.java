package core.base.javaonly.withoutdi.payroll;


import core.base.javaonly.connection.ConnectionConst;
import core.base.javaonly.withoutdi.employee.*;
import core.base.javaonly.withoutdi.incentive.SalaryRatioIncentivePolicy;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import static core.base.javaonly.connection.ConnectionConst.*;

public class PayrollApp {
    public static void main(String[] args) {
        DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        EmployeeService employeeService = new EmployeeServiceImpl(
//                new JDBCEmployeeRepository(dataSource)
                new MemoryEmployeeRepository()
        );
        PayrollService payrollService = new PayrollServiceImpl(
//                new JDBCPayrollRepository(dataSource)
                new SalaryRatioIncentivePolicy(JobLevel.Manager, 0.1),
                new JDBCEmployeeRepository(dataSource),
                new JDBCPayrollRepository(dataSource)
        );

        Long testId = 2L;
        Employee findEmployee = employeeService.findEmployee(testId);

        if (findEmployee == null) {
            Employee employee = new Employee(2L , "홍길동", JobLevel.Manager, 100000);
            employeeService.register(employee);
        }

        Employee employee = employeeService.findEmployee(testId);


        Payroll payroll = payrollService.createPayroll(employee.getId(), "2024년 4분기 업무", employee.getSalary());
        System.out.println(payroll);
    }
}
