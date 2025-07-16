package core.base.javaonly.withoutdi.payroll;

import core.base.javaonly.withoutdi.employee.*;
import core.base.javaonly.withoutdi.incentive.FixedIncentivePolicy;
import core.base.javaonly.withoutdi.incentive.IncentivePolicy;
import core.base.javaonly.withoutdi.incentive.SalaryRatioIncentivePolicy;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import static core.base.javaonly.connection.ConnectionConst.*;

public class PayrollServiceImpl implements PayrollService {

//    private final IncentivePolicy incentivePolicy = new FixedIncentivePolicy(JobLevel.Manager, 1000);
    private final IncentivePolicy incentivePolicy = new SalaryRatioIncentivePolicy(JobLevel.Manager, 0.1);

//    private final PayrollRepository payrollRepository = new MemoryPayrollRepository();

    private final DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
    private final EmployeeRepository employeeRepository = new JDBCEmployeeRepository(dataSource);
    private final PayrollRepository payrollRepository = new JDBCPayrollRepository(dataSource);


    @Override
    public Payroll createPayroll(Long employeeId, String task, int baseSalary) {
        Employee employee = employeeRepository.findById(employeeId);
        int bonusAmount = incentivePolicy.calculateIncentive(employee, baseSalary);

        Payroll payroll = new Payroll(employeeId, task, baseSalary, bonusAmount);

        payrollRepository.save(payroll);

        return payroll;
    }
}
