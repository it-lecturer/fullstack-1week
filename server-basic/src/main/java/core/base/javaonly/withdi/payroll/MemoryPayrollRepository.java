package core.base.javaonly.withdi.payroll;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryPayrollRepository implements PayrollRepository {
    private static Map<UUID, Payroll> store = new HashMap<>();

    @Override
    public void save(Payroll payroll) {
        store.put(payroll.getId(), payroll);
    }

    @Override
    public Payroll findById(UUID payrollId) {
        return store.get(payrollId);
    }
}
