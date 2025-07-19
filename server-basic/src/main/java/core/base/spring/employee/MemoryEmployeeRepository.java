package core.base.spring.employee;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
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
