package core.base.spring.incentive;

import core.base.spring.employee.Employee;
import core.base.spring.employee.JobLevel;

public interface IncentivePolicy {
    /** 인센티브 대상자인지 반환 */
    boolean supports(JobLevel jobLevel);
    /** 계산된 인센티브 반환 */
    int calculateIncentive(Employee employee, int baseSalary);
}
