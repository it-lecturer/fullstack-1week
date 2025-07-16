package core.base.javaonly.withoutdi.payroll;


import core.base.javaonly.withoutdi.employee.Employee;
import core.base.javaonly.withoutdi.employee.EmployeeService;
import core.base.javaonly.withoutdi.employee.EmployeeServiceImpl;
import core.base.javaonly.withoutdi.employee.JobLevel;

public class PayrollApp {
    public static void main(String[] args) {
        EmployeeService employeeService = new EmployeeServiceImpl();
        PayrollService payrollService = new PayrollServiceImpl();

        Long testId = 2L;
        Employee findEmployee = employeeService.findEmployee(testId);

        if (findEmployee == null) {
            Employee employee = new Employee(2L , "홍길동", JobLevel.Manager, 100000);
            employeeService.register(employee);
        }

        Employee employee = employeeService.findEmployee(testId);


        Payroll payroll = payrollService.createPayroll(employee.getId(), "2024년 4분기 업무", employee.getSalary());
        System.out.println(payroll);
    }
}
