package learn.calorietracker.data;

import learn.calorietracker.models.LogEntry;
import learn.calorietracker.models.LogEntryType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class LogEntryJdbcTemplateRepositoryTest {

    @Autowired
    LogEntryJdbcTemplateRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    static boolean hasSetUp = false;

    @BeforeEach
    void setup() {
        if (!hasSetUp) {
            hasSetUp = true;
            jdbcTemplate.update("call set_known_good_state();");
        }
    }

    @Test
    void shouldFindAll() {
        List<LogEntry> all = repository.findAll();

        assertNotNull(all);
        assertTrue(all.size() >= 2);

        LogEntry entry = all.get(0);

        assertTrue(entry.getId() >= 1);
        assertNotNull(entry.getDescription());

    }

    @Test
    void shouldFindByType() {
        List<LogEntry> all = repository.findByType(LogEntryType.LUNCH);

        assertNotNull(all);
        assertEquals(1, all.size());

        LogEntry entry = all.get(0);

        assertEquals(2, entry.getId());
        assertNotNull(entry.getDescription());
    }

    @Test
    void shouldFindById() {
        LogEntry entry = repository.findById(1);

        assertNotNull(entry);
    }

    @Test
    void shouldNotFindMissingId() {
        LogEntry entry = repository.findById(10000);

        assertNull(entry);
    }

    @Test
    void shouldCreateEntry() {
        LogEntry newEntry = new LogEntry();

        newEntry.setType(LogEntryType.SNACK);
        newEntry.setCalories(10000);
        newEntry.setDescription("All the Pizza ever");
        newEntry.setLoggedOn("2020-10-01");

        LogEntry entry = repository.create(newEntry);

        assertNotNull(entry);
        assertTrue(entry.getId() >= 4);
    }

    @Test
    void shouldUpdate() {
        //arrange
        LogEntry entry = new LogEntry();
        entry.setId(1);
        entry.setDescription("new description");
        entry.setLoggedOn("2020-01-01");
        entry.setType(LogEntryType.BREAKFAST);
        entry.setCalories(5);
        //act
        boolean updated = repository.update(entry);
        //assert
        assertTrue(updated);
    }

    @Test
    void shouldNotUpdateIfRecordDoesNotExist() {
        //arrange
        LogEntry entry = new LogEntry();
        entry.setId(10000);
        entry.setType(LogEntryType.BREAKFAST);
        //act
        boolean updated = repository.update(entry);
        //assert
        assertFalse(updated);
    }

    @Test
    void shouldDelete() {
        //act
        boolean deleted = repository.delete(3);
        //assert
        assertTrue(deleted);
    }

    @Test
    void shouldNotDeleteMissingRecord() {
        //act
        boolean deleted = repository.delete(10000);
        //assert
        assertFalse(deleted);
    }
}
