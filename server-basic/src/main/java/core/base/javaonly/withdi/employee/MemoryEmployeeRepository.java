package core.base.javaonly.withdi.employee;

import java.util.HashMap;
import java.util.Map;

public class MemoryEmployeeRepository implements EmployeeRepository {
    private static Map<Long, Employee> store = new HashMap<>();

    @Override
    public void save(Employee employee) {
        store.put(employee.getId(), employee);
    }

    @Override
    public Employee findById(Long employeeId) {
        return store.get(employeeId);
    }
}
