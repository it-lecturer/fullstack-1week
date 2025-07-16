package core.base.javaonly.withoutdi.incentive;

import core.base.javaonly.withoutdi.employee.Employee;
import core.base.javaonly.withoutdi.employee.JobLevel;

public interface IncentivePolicy {
    boolean supports(JobLevel jobLevel);
    int calculateIncentive(Employee employee, int baseSalary);
}
