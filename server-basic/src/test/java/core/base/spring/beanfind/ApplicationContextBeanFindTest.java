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
        // 스프링 빈에 부모타입으로 조회하면 자식 타입도 조회된다. Object 타입으로 조회하면 모든 스프링 빈을 조회한다.
        EmployeeService employeeService = ac.getBean(EmployeeService.class);
        Assertions.assertThat(employeeService).isInstanceOf(EmployeeServiceImpl.class);
    }

    @Test
    @DisplayName("구체 타입으로 조회")
    void findBeanByName2() {
        // 구체타입으로 조회하면 변경시 유연성이 떨어진다.
        EmployeeServiceImpl employeeService = ac.getBean(EmployeeServiceImpl.class);
        Assertions.assertThat(employeeService).isInstanceOf(EmployeeServiceImpl.class);
    }
}
