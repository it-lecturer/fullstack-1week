package core.base.javaonly.withoutdi.employee;

public class EmployeeApp {


    public static void main(String[] args) {
        EmployeeService employeeService = new EmployeeServiceImpl();
        Employee employee = new Employee(1L, "김상헌", JobLevel.Assistant, 50000);

        employeeService.register(employee);

        Employee findEmployee = employeeService.findEmployee(1L);

        System.out.println("new employee = " + employee);
        System.out.println("find employee = " + findEmployee);
    }
}
