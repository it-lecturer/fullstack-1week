package core.base.spring.payroll;

import core.base.AppConfig;
import core.base.spring.employee.Employee;
import core.base.spring.employee.EmployeeService;
import core.base.spring.employee.JobLevel;

public class PayrollApp {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        EmployeeService employeeService = appConfig.employeeService();
        PayrollService payrollService = appConfig.payrollService();

        Long testId = 2L;
        Employee employee = new Employee(2L , "홍길동", JobLevel.Manager, 100000);
        employeeService.register(employee);

        Payroll payroll = payrollService.createPayroll(employee.getId(), "2024년 4분기 업무", employee.getSalary());
        System.out.println(payroll);
    }
}
