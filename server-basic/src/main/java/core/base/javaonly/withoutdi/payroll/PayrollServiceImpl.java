package core.base.javaonly.withoutdi.payroll;

import core.base.javaonly.withoutdi.employee.*;
import core.base.javaonly.withoutdi.incentive.FixedIncentivePolicy;
import core.base.javaonly.withoutdi.incentive.IncentivePolicy;
import core.base.javaonly.withoutdi.incentive.SalaryRatioIncentivePolicy;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import static core.base.javaonly.connection.ConnectionConst.*;

public class PayrollServiceImpl implements PayrollService {

    private IncentivePolicy incentivePolicy;
    private DataSource dataSource;
    private EmployeeRepository employeeRepository;
    private PayrollRepository payrollRepository;

    @Override
    public Payroll createPayroll(Long employeeId, String task, int baseSalary) {
        Employee employee = employeeRepository.findById(employeeId);
        int bonusAmount = incentivePolicy.calculateIncentive(employee, baseSalary);

        Payroll payroll = new Payroll(employeeId, task, baseSalary, bonusAmount);

        payrollRepository.save(payroll);

        return payroll;
    }
}
