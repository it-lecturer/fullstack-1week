package core.base.spring.employee;

import core.base.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class EmployeeApp {
    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        EmployeeService employeeService =  applicationContext.getBean("employeeService", EmployeeService.class);

        Employee employee = new Employee(1L, "김상헌", JobLevel.Assistant, 50000);

        employeeService.register(employee);

        Employee findEmployee = employeeService.findEmployee(1L);

        System.out.println("new employee = " + employee);
        System.out.println("find employee = " + findEmployee);
    }
}
