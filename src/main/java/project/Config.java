package project;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.sql.SQLException;

@Configuration
public class Config {

    @Bean
    public DB getDB() throws SQLException {
        return new DB("jdbc:mysql://db/beerchopper?user=root&password=123");
    }
}
