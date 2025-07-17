package core.base.spring.payroll;

import core.base.AppConfig;
import core.base.spring.employee.Employee;
import core.base.spring.employee.EmployeeService;
import core.base.spring.employee.JobLevel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class PayrollServiceTest {
    private PayrollService payrollService;
    private EmployeeService employeeService;

    @BeforeEach
    void beforeEach() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        payrollService =  ac.getBean("payrollService", PayrollService.class);
        employeeService = ac.getBean("employeeService", EmployeeService.class);
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