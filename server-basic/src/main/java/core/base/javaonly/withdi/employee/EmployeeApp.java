package core.base.javaonly.withdi.employee;

import core.base.AppConfig;
import core.base.JavaOnlyAppConfig;

public class EmployeeApp {
    public static void main(String[] args) {
        JavaOnlyAppConfig appConfig = new JavaOnlyAppConfig();
        EmployeeService employeeService = appConfig.employeeService();

        Employee employee = new Employee(1L, "김상헌", JobLevel.Assistant, 50000);

        employeeService.register(employee);

        Employee findEmployee = employeeService.findEmployee(1L);

        System.out.println("new employee = " + employee);
        System.out.println("find employee = " + findEmployee);
    }
}
