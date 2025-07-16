package core.base.javaonly.withoutdi.payroll;

import core.base.javaonly.withoutdi.employee.*;
import core.base.javaonly.withoutdi.incentive.SalaryRatioIncentivePolicy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import static core.base.javaonly.connection.ConnectionConst.*;
import static org.junit.jupiter.api.Assertions.*;

class PayrollServiceTest {
    private DataSource dataSource;
    private PayrollService payrollService;
    private EmployeeService employeeService;


    @BeforeEach
    void beforeEach() {
        dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        employeeService = new EmployeeServiceImpl(
//                new JDBCEmployeeRepository(dataSource)
                new JDBCEmployeeRepository(dataSource)
        );
        payrollService = new PayrollServiceImpl(
                new SalaryRatioIncentivePolicy(JobLevel.Manager, 0.1),
                new JDBCEmployeeRepository(dataSource),
                new JDBCPayrollRepository(dataSource)
        );
    }

    @Test
    @DisplayName("인센티브 반영 급여 (매니저 직급 이상)")
    void createPayroll() {
        // given
        Long employeeId = 1L;
        Employee employee = new Employee(employeeId, "홍길동", JobLevel.Manager, 10000);
        employeeService.register(employee);

        Employee findEmployee = employeeService.findEmployee(employeeId);

        // when
        Payroll payroll =  payrollService.createPayroll(findEmployee.getId(), "2025년 7월 급여", employee.getSalary());

        // then
        Assertions.assertThat(payroll.calculateFinalSalary()).isEqualTo(11000);
    }

    @Test
    @DisplayName("인센티브 반영 급여 (매니저 직급 미만 - 반영 X)")
    void createPayrollWithoutIncentive() {
        // given
        Long employeeId = 100L;
        Employee employee = new Employee(employeeId, "김사원", JobLevel.Assistant, 500);
        employeeService.register(employee);

        Employee findEmployee = employeeService.findEmployee(employeeId);

        // when
        Payroll payroll =  payrollService.createPayroll(findEmployee.getId(), "2025년 7월 급여", employee.getSalary());

        // then
        Assertions.assertThat(payroll.calculateFinalSalary()).isEqualTo(500);
    }
}