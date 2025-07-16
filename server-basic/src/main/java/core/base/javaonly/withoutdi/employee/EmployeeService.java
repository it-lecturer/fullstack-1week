package core.base.javaonly.withoutdi.employee;

public interface EmployeeService {
    void register(Employee employee);
    Employee findEmployee(Long employeeId);
}
