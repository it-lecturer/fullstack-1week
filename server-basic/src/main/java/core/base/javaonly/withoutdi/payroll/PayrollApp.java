package core.base.javaonly.withoutdi.payroll;


import core.base.javaonly.withoutdi.employee.Employee;
import core.base.javaonly.withoutdi.employee.EmployeeService;
import core.base.javaonly.withoutdi.employee.EmployeeServiceImpl;
import core.base.javaonly.withoutdi.employee.JobLevel;

public class PayrollApp {
    public static void main(String[] args) {
        EmployeeService employeeService = new EmployeeServiceImpl();
        PayrollService payrollService = new PayrollServiceImpl();

        Employee employee = new Employee(1L , "홍길동", JobLevel.Manager, 50000);
        employeeService.register(employee);

        Payroll payroll = payrollService.createPayroll(1L, "2024년 4분기 업무", employee.getSalary());
        System.out.println(payroll);
    }
}
