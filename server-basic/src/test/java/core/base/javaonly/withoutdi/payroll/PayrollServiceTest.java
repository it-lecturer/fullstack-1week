package core.base.javaonly.withoutdi.payroll;

import core.base.javaonly.withoutdi.employee.Employee;
import core.base.javaonly.withoutdi.employee.EmployeeService;
import core.base.javaonly.withoutdi.employee.EmployeeServiceImpl;
import core.base.javaonly.withoutdi.employee.JobLevel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PayrollServiceTest {

    PayrollService payrollService = new PayrollServiceImpl();
    EmployeeService employeeService = new EmployeeServiceImpl();

    @Test
    @DisplayName("급여 생성 테스트")
    void createPayroll() {


        // given
        Long employeeId = 1L;
        Employee employee = new Employee(employeeId, "홍길동", JobLevel.President);
        employeeService.register(employee);

        Employee findEmployee = employeeService.findEmployee(employeeId);


        PayrollService payrollService = new PayrollServiceImpl();

        // when
        Payroll payroll =  payrollService.createPayroll(findEmployee.getId(), "2025년 7월 급여", 50000);

        // then
        Assertions.assertThat(payroll.calculateFinalSalary()).isEqualTo(51000);
    }
}