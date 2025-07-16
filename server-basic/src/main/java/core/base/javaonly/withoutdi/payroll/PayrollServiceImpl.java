package core.base.javaonly.withoutdi.payroll;

import core.base.javaonly.withoutdi.employee.Employee;
import core.base.javaonly.withoutdi.employee.EmployeeRepository;
import core.base.javaonly.withoutdi.employee.JobLevel;
import core.base.javaonly.withoutdi.employee.MemoryEmployeeRepository;
import core.base.javaonly.withoutdi.incentive.FixedIncentivePolicy;
import core.base.javaonly.withoutdi.incentive.IncentivePolicy;

public class PayrollServiceImpl implements PayrollService {
    private final EmployeeRepository employeeRepository = new MemoryEmployeeRepository();
    private final IncentivePolicy incentivePolicy = new FixedIncentivePolicy(JobLevel.Manager, 1000);

    @Override
    public Payroll createPayroll(Long employeeId, String task, int baseSalary) {
        Employee employee = employeeRepository.findById(employeeId);
        int bonusAmount = incentivePolicy.calculateIncentive(employee, baseSalary);
        return new Payroll(employeeId, task, baseSalary, bonusAmount);
    }
}
