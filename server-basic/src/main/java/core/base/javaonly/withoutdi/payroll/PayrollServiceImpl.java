package core.base.javaonly.withoutdi.payroll;

import core.base.javaonly.withoutdi.employee.*;
import core.base.javaonly.withoutdi.incentive.IncentivePolicy;

public class PayrollServiceImpl implements PayrollService {

    private final IncentivePolicy incentivePolicy;
    private final EmployeeRepository employeeRepository;
    private final PayrollRepository payrollRepository;

    public PayrollServiceImpl(IncentivePolicy incentivePolicy, EmployeeRepository employeeRepository, PayrollRepository payrollRepository) {
        this.incentivePolicy = incentivePolicy;
        this.employeeRepository = employeeRepository;
        this.payrollRepository = payrollRepository;
    }

    @Override
    public Payroll createPayroll(Long employeeId, String task, int baseSalary) {
        Employee employee = employeeRepository.findById(employeeId);
        int bonusAmount = incentivePolicy.calculateIncentive(employee, baseSalary);

        Payroll payroll = new Payroll(employeeId, task, baseSalary, bonusAmount);

        payrollRepository.save(payroll);

        return payroll;
    }
}
