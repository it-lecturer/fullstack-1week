package core.base.javaonly.withoutdi.payroll;

import java.util.UUID;

public interface PayrollRepository {
    /** 저장 */
    void save(Payroll payroll);
    /** ID로 찾기 */
    Payroll findById(UUID payrollId);
}
