package core.base.spring.payroll;

public interface PayrollService {
    /** 급여 생성 */
    Payroll createPayroll(Long employeeId, String task, int baseSalary);
}
