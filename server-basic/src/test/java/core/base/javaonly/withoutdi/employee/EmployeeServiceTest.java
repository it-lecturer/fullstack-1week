package core.base.javaonly.withoutdi.employee;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {
    @Test
    @DisplayName("직원 등록이 잘되는지 확인")
    void register() {
        // given
        Employee employee = new Employee(1L, "홍길동", JobLevel.Manager, 50000);
        EmployeeService employeeService = new EmployeeServiceImpl();

        // when
        employeeService.register(employee);

        // then
        Employee findEmployee = employeeService.findEmployee(1L);

        Assertions.assertThat(findEmployee).isEqualTo(employee);
    }
}