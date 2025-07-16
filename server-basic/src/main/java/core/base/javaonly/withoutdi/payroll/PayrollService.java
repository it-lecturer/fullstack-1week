package core.base.javaonly.withoutdi.payroll;

public interface PayrollService {
    Payroll createPayroll(Long employeeId, String task, int baseSalary);
}
