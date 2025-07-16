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
        // 같은 EmployeeRepository 인스턴스를 공유
        EmployeeRepository employeeRepository = new JDBCEmployeeRepository(dataSource);
        PayrollRepository payrollRepository = new JDBCPayrollRepository(dataSource);
        EmployeeService employeeService = new EmployeeServiceImpl(employeeRepository);
        PayrollService payrollService = new PayrollServiceImpl(
                new SalaryRatioIncentivePolicy(JobLevel.Manager, 0.1),
                employeeRepository,  // 같은 인스턴스 사용
                payrollRepository
        );


        Long testId = 2L;
        Employee employee = new Employee(2L , "홍길동", JobLevel.Manager, 100000);
        employeeService.register(employee);

        Payroll payroll = payrollService.createPayroll(employee.getId(), "2024년 4분기 업무", employee.getSalary());
        System.out.println(payroll);
    }
}
