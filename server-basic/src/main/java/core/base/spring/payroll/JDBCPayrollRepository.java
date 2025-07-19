package core.base.spring.payroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.UUID;

@Repository
@Primary
public class JDBCPayrollRepository implements PayrollRepository {
    private final JdbcTemplate template;

    @Autowired
    public JDBCPayrollRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Payroll payroll) {
        String sql = "INSERT INTO payroll (employee_id, task, base_salary, bonus_amount) VALUES (?, ?, ?, ?)";

        template.update(sql, payroll.getEmployeeId(), payroll.getTask(), payroll.getBaseSalary(), payroll.getBonusAmount());
    }

    @Override
    public Payroll findById(UUID payrollId) {
        String sql = "SELECT * FROM payroll WHERE id = ?";
        return template.queryForObject(sql, payrollRowWrapper(), payrollId);
    }

    private RowMapper<Payroll> payrollRowWrapper() {
        return (rs, rowNum) -> {
            return new Payroll(
                    rs.getLong("employee_id"),
                    rs.getString("task"),
                    rs.getInt("base_salary"),
                    rs.getInt("bonus_amount")
            );
        };
    }
}
