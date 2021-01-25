package learn.calorietracker.domain;

import learn.calorietracker.data.DataAccessException;
import learn.calorietracker.data.doubles.LogEntryRepositoryDouble;
import learn.calorietracker.models.LogEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LogEntryServiceTest {

    private LogEntryService service;

    @BeforeEach
    void setUp() {
        LogEntryRepositoryDouble repository = new LogEntryRepositoryDouble();
        service = new LogEntryService(repository);
    }

    @Test
    void shouldFindAll() throws DataAccessException {
        // arrange

        // act
        List<LogEntry> results = service.findAll();

        // assert
        assertNotNull(results);
        assertEquals(3, results.size());
    }
}