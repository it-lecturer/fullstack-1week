package core.base.spring.payroll;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
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
