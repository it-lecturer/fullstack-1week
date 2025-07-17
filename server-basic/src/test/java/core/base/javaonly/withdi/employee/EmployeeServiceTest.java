package core.base.javaonly.withdi.employee;

import core.base.AppConfig;
import core.base.JavaOnlyAppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmployeeServiceTest {
    private EmployeeService employeeService;

    @BeforeEach
    void beforeEach() {
        JavaOnlyAppConfig appConfig = new JavaOnlyAppConfig();
        employeeService = appConfig.employeeService();
    }

    @Test
    @DisplayName("직원 등록이 잘되는지 확인")
    void register() {
        // given
        Employee employee = new Employee(1L, "홍길동", JobLevel.Manager, 50000);
        // when
        employeeService.register(employee);

        // then
        Employee findEmployee = employeeService.findEmployee(1L);

        Assertions.assertThat(findEmployee.getName()).isSameAs(employee.getName());
    }
}