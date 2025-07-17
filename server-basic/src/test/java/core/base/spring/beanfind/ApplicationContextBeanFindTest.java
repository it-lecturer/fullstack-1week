package core.base.spring.beanfind;

import core.base.AppConfig;
import core.base.spring.employee.EmployeeService;
import core.base.spring.employee.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextBeanFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName() {
        EmployeeService employeeService = ac.getBean("employeeService", EmployeeService.class);
        Assertions.assertThat(employeeService).isInstanceOf(EmployeeServiceImpl.class);
    }

    @Test
    @DisplayName("이름 없이 타입만으로 조회 (상속된다 상위타입 조회시 하위 타입들의 빈들 다 조회)")
    void findBeanByType() {
        EmployeeService employeeService = ac.getBean(EmployeeService.class);
        Assertions.assertThat(employeeService).isInstanceOf(EmployeeServiceImpl.class);
    }
}
