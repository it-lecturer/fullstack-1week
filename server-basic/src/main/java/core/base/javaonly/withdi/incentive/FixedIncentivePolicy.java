package core.base.javaonly.withdi.incentive;

import core.base.javaonly.withdi.employee.Employee;
import core.base.javaonly.withdi.employee.JobLevel;

public class FixedIncentivePolicy implements IncentivePolicy {
    private final JobLevel jobLevel;
    private final int fixedIncentive;

    public FixedIncentivePolicy(JobLevel jobLevel, int fixedIncentive) {
        this.jobLevel = jobLevel;
        this.fixedIncentive = fixedIncentive;
    }

    @Override
    public boolean supports(JobLevel jobLevel) {
        return this.jobLevel.ordinal() >= jobLevel.ordinal();
    }

    @Override
    public int calculateIncentive(Employee employee, int baseSalary) {
        return employee.getJobLevel().ordinal() >= jobLevel.ordinal() ? fixedIncentive : 0;
    }
}
