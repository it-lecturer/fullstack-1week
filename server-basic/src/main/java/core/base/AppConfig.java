package core.base;

import com.zaxxer.hikari.HikariDataSource;
import core.base.spring.employee.JobLevel;
import core.base.spring.incentive.IncentivePolicy;
import core.base.spring.incentive.SalaryRatioIncentivePolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import static core.base.javaonly.connection.ConnectionConst.*;

@ComponentScan
@Configuration
public class AppConfig {
    @Bean
    public IncentivePolicy incentivePolicy() {
        return new SalaryRatioIncentivePolicy(JobLevel.Manager, 0.1);
    }

    @Bean
    public DataSource dataSource() {
        DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        return dataSource;
    }
}
