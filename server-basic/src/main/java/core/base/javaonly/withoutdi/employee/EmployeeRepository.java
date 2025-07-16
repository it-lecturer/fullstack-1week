package core.base.javaonly.withoutdi.employee;

public interface EmployeeRepository {
    void save(Employee employee);
    Employee findById(Long employeeId);
}
