package core.base.spring.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
@Primary
public class JDBCEmployeeRepository implements EmployeeRepository {
    private final JdbcTemplate template;

    @Autowired
    public JDBCEmployeeRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Employee employee) {
        String sql = "INSERT INTO employee (id, name, job_level, salary) VALUES (?, ?, ?, ?)";
        template.update(sql, employee.getId(), employee.getName(), employee.getJobLevel().name(), employee.getSalary());
    }

    @Override
    public Employee findById(Long employeeId) {
        String sql = "SELECT * FROM employee WHERE id = ?";

        return template.queryForObject(sql, employeeRowWrapper(), employeeId);
    }

    private RowMapper<Employee> employeeRowWrapper() {
        return (rs, rowNum) -> {
            return new Employee(
                    rs.getLong("id"),
                    rs.getString("name"),
                    JobLevel.valueOf(rs.getString("job_level")),
                    rs.getInt("salary")
            );
        };
    }
}
