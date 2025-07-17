package core.base.spring.employee;

import core.base.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class EmployeeServiceTest {
    private EmployeeService employeeService;

    @BeforeEach
    void beforeEach() {
        ApplicationContext ac =  new AnnotationConfigApplicationContext(AppConfig.class);

        employeeService =  ac.getBean("employeeService", EmployeeService.class);
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