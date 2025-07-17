package core.base.spring.employee;

public interface EmployeeService {
    /** 직원 등록 */
    void register(Employee employee);
    /** 직원 ID로 직원 찾기 */
    Employee findEmployee(Long employeeId);
}
