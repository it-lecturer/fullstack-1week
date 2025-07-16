package core.base.javaonly.withdi.employee;

public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void register(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public Employee findEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }
}
