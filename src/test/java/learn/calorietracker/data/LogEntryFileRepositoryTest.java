package learn.calorietracker.data;

import learn.calorietracker.models.LogEntry;
import learn.calorietracker.models.LogEntryType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LogEntryFileRepositoryTest {

    // TODO create seed data and copy to test data file
    // TODO @BeforeEach

    @Test
    void shouldFindAll() throws DataAccessException {
        // arrange
        LogEntryRepository repository = new LogEntryFileRepository("data/log-entries.csv");

        // act
        List<LogEntry> entries = repository.findAll();

        // assert
        assertEquals(3, entries.size());
    }

    @Test
    void shouldFindByType() throws DataAccessException {
        // arrange
        LogEntryRepository repository = new LogEntryFileRepository("data/log-entries.csv");

        // act
        List<LogEntry> entries = repository.findByType(LogEntryType.BREAKFAST);

        // assert
        assertEquals(1, entries.size());
    }

    @Test
    void shouldReturnEmptyListIfTypeNotFound() throws DataAccessException {
        // arrange
        LogEntryRepository repository = new LogEntryFileRepository("data/log-entries.csv");

        // act
        List<LogEntry> entries = repository.findByType(LogEntryType.SNACK);

        // assert
        assertEquals(0, entries.size());
    }

    @Test
    void shouldFindById() throws DataAccessException {
        // arrange
        LogEntryRepository repository = new LogEntryFileRepository("data/log-entries.csv");

        // act
        LogEntry entry = repository.findById(1);

        // assert
        assertNotNull(entry);
        assertEquals(1, entry.getId());
        assertEquals("2020-01-01 09:00 AM", entry.getLoggedOn());
        assertEquals(LogEntryType.BREAKFAST, entry.getType());
        assertEquals("Scrambled eggs", entry.getDescription());
        assertEquals(210, entry.getCalories());
    }

    @Test
    void findByIdShoundReturnNullForBadId() throws DataAccessException {
        // arrange
        LogEntryRepository repository = new LogEntryFileRepository("data/log-entries.csv");

        // act
        LogEntry entry = repository.findById(999);

        // assert
        assertNull(entry);
    }

    @Test
    void shouldCreate() throws DataAccessException {
        // arrange
        LogEntryRepository repository = new LogEntryFileRepository("data/log-entries.csv");
        LogEntry entry = new LogEntry(0, "2020-02-01", LogEntryType.BREAKFAST, "Toast", 100);

        // act
        LogEntry result = repository.create(entry);

        List<LogEntry> entries = repository.findAll();
        LogEntry newEntry = entries.get(entries.size() - 1); // get the last entry

        // assert
        assertNotNull(result);
        assertEquals(4, result.getId());
        assertModelValues(newEntry, 4, "2020-02-01", LogEntryType.BREAKFAST, "Toast", 100);
    }

    @Test
    void shouldNotCreateWhenPassedNull() {
        // TODO
    }

    // TODO Can we create an entry if there's not a file at all

    /**
     * Helper method to assert model values (totally optional).
     * @param entry
     * @param id
     * @param loggedOn
     * @param type
     * @param description
     * @param calories
     */
    private void assertModelValues(LogEntry entry, int id, String loggedOn, LogEntryType type,
                                   String description, int calories) {
        assertNotNull(entry);
        assertEquals(id, entry.getId());
        assertEquals(loggedOn, entry.getLoggedOn());
        assertEquals(type, entry.getType());
        assertEquals(description, entry.getDescription());
        assertEquals(calories, entry.getCalories());
    }
}




















