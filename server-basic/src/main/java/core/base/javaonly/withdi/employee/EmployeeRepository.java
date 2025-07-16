package core.base.javaonly.withdi.employee;

public interface EmployeeRepository {
    /** 저장 */
    void save(Employee employee);
    /** ID로 찾기 */
    Employee findById(Long employeeId);
}
