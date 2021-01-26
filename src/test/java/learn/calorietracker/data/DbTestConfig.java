package learn.calorietracker.data;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@ComponentScan
public class DbTestConfig {

    @Bean
    public DataSource getDataSource() {
        MysqlDataSource result = new MysqlDataSource();
        result.setUrl("jdbc:mysql://localhost:3306/calorie_tracker");
        result.setUser("root");
        result.setPassword("pwd123123");
        return result;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
