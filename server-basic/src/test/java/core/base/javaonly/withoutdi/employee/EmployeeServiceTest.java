package core.base.javaonly.withoutdi.employee;

import core.base.javaonly.connection.ConnectionConst;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import static core.base.javaonly.connection.ConnectionConst.*;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {
    private EmployeeService employeeService;
    private DataSource dataSource;

    @BeforeEach
    void beforeEach() {
        dataSource =new  DriverManagerDataSource(URL, USERNAME, PASSWORD);
        employeeService = new EmployeeServiceImpl(new JDBCEmployeeRepository(dataSource));
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