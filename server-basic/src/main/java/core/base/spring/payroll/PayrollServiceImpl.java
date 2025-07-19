package core.base.spring.payroll;

import core.base.spring.employee.Employee;
import core.base.spring.employee.EmployeeRepository;
import core.base.spring.incentive.IncentivePolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayrollServiceImpl implements PayrollService {

    private final IncentivePolicy incentivePolicy;
    private final EmployeeRepository employeeRepository;
    private final PayrollRepository payrollRepository;

    @Autowired
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
