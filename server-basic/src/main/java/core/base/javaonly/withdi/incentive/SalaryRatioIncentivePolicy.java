package core.base.javaonly.withdi.incentive;

import core.base.javaonly.withdi.employee.Employee;
import core.base.javaonly.withdi.employee.JobLevel;

public class SalaryRatioIncentivePolicy implements IncentivePolicy {
    private final JobLevel jobLevel;
    private final double ratio; // 0.1 = 10%

    public SalaryRatioIncentivePolicy(JobLevel jobLevel, double ratio) {
        this.jobLevel = jobLevel;
        this.ratio = ratio;
    }

    @Override
    public boolean supports(JobLevel jobLevel) {
        return this.jobLevel.ordinal() >= jobLevel.ordinal();
    }

    @Override
    public int calculateIncentive(Employee employee, int baseSalary) {
        return employee.getJobLevel().ordinal() >= jobLevel.ordinal()
                ? (int) Math.round(baseSalary * ratio)
                : 0;

    }
}
