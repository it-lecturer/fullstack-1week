package core.base.spring.payroll;

import core.base.AppConfig;
import core.base.spring.employee.Employee;
import core.base.spring.employee.EmployeeService;
import core.base.spring.employee.JobLevel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PayrollApp {
    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        EmployeeService employeeService = ac.getBean("employeeService", EmployeeService.class);
        PayrollService payrollService = ac.getBean("payrollService", PayrollService.class);

        Long testId = 2L;
        Employee employee = new Employee(2L , "홍길동", JobLevel.Manager, 100000);
        employeeService.register(employee);

        Payroll payroll = payrollService.createPayroll(employee.getId(), "2024년 4분기 업무", employee.getSalary());
        System.out.println(payroll);
    }
}
