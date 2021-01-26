package learn.calorietracker.data;

import learn.calorietracker.models.LogEntry;
import learn.calorietracker.models.LogEntryType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LogEntryJdbcTemplateRepositoryTest {
    LogEntryJdbcTemplateRepository repository;

    public LogEntryJdbcTemplateRepositoryTest() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DbTestConfig.class);
        repository = context.getBean(LogEntryJdbcTemplateRepository.class);
    }

    @BeforeAll
    static void oneTimeSetup() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DbTestConfig.class);
        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
        jdbcTemplate.update("call set_known_good_state();");
    }

    @Test
    void shouldFindAll() throws DataAccessException {
        List<LogEntry> all = repository.findAll();


        assertNotNull(all);
        assertTrue(all.size() >= 3);

        LogEntry entry = all.get(0);

        assertTrue(entry.getId() >= 1);
        assertNotNull(entry.getDescription());

    }

    @Test
    void shouldFindById() throws DataAccessException{
        LogEntry entry = repository.findById(1);

        assertNotNull(entry);
    }

    @Test
    void shouldNotFindMissingId() throws DataAccessException {
        LogEntry entry = repository.findById(10000);

        assertNull(entry);
    }

    @Test
    void shouldCreateEntry() throws DataAccessException {
        LogEntry newEntry = new LogEntry();

        newEntry.setType(LogEntryType.SNACK);
        newEntry.setCalories(10000);
        newEntry.setDescription("All the Pizza ever");
        newEntry.setLoggedOn("2020-10-01");

        LogEntry entry = repository.create(newEntry);

        assertNotNull(entry);
        assertTrue(entry.getId() >= 4);
    }
}
