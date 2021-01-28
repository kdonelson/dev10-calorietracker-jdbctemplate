package learn.calorietracker.domain;

import learn.calorietracker.data.LogEntryRepository;
import learn.calorietracker.models.LogEntry;
import learn.calorietracker.models.LogEntryType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class LogEntryServiceTest {

    @MockBean
    private LogEntryRepository repository;

    @Autowired
    private LogEntryService service;

    @Test
    void shouldFindAll() {
        // arrange
        List<LogEntry> entryOut = new ArrayList<>();
        entryOut.add(new LogEntry());

        when(repository.findAll()).thenReturn(entryOut);

        // act
        List<LogEntry> results = service.findAll();

        // assert
        assertNotNull(results);
        assertEquals(1, results.size());
    }

    @Test
    void shouldAdd() {
        //arrange
        LogEntry entryIn = new LogEntry();
        entryIn.setLoggedOn("2020-10-01");
        entryIn.setDescription("something");
        entryIn.setType(LogEntryType.SNACK);
        entryIn.setCalories(1);
        LogEntry entryOut = new LogEntry();

        when(repository.create(entryIn)).thenReturn(entryOut);
        //act

        LogEntryResult result = service.create(entryIn);
        //assert
        assertTrue(result.isSuccessful());
        assertNotNull(result.getPayload());
        assertEquals(entryOut, result.getPayload());
    }

    @Test
    void shouldNotAddIfProvidedId() {
        //arrange
        LogEntry entryIn = new LogEntry();
        entryIn.setId(1);
        //act
        LogEntryResult result = service.create(entryIn);

        //assert
        assertFalse(result.isSuccessful());
        assertEquals(1,result.getMessages().size());
        assertTrue(result.getMessages().contains("id is auto-generated and cannot be provided"));
    }

    @Test
    void shouldNotAddIfEntryIsNull() {
        //act
        LogEntryResult result = service.create(null);

        //assert
        assertFalse(result.isSuccessful());
        assertEquals(1,result.getMessages().size());
        assertTrue(result.getMessages().contains("entry cannot be null"));
    }

    @Test
    void shouldNotAddNullDescription() {
        //arrange
        LogEntry entryIn = new LogEntry();
        entryIn.setLoggedOn("2020-10-01");
        entryIn.setType(LogEntryType.SNACK);
        entryIn.setCalories(1);

        //act
        LogEntryResult result = service.create(entryIn);
        //assert
        assertFalse(result.isSuccessful());
        assertTrue(result.getMessages().contains("description is required"));
    }

    @Test
    void shouldNotAddTooLongDescription() {
        //arrange
        LogEntry entryIn = new LogEntry();
        entryIn.setLoggedOn("2020-10-01");
        entryIn.setDescription("a".repeat(101)); //max length 100
        entryIn.setType(LogEntryType.SNACK);
        entryIn.setCalories(1);

        //act
        LogEntryResult result = service.create(entryIn);
        //assert
        assertFalse(result.isSuccessful());
        assertTrue(result.getMessages().contains("description must be less than or equal to 100 characters"));
    }

    @Test
    void shouldNotAddNullLoggedOn() {
        //arrange
        LogEntry entryIn = new LogEntry();
        entryIn.setDescription("something"); //max length 100
        entryIn.setType(LogEntryType.SNACK);
        entryIn.setCalories(1);

        //act
        LogEntryResult result = service.create(entryIn);
        //assert
        assertFalse(result.isSuccessful());
        assertTrue(result.getMessages().contains("logged date is required"));
    }

    @Test
    void shouldNotAddNegativeCalories() {
        //arrange
        LogEntry entryIn = new LogEntry();
        entryIn.setLoggedOn("2020-10-01");
        entryIn.setDescription("something"); //max length 100
        entryIn.setType(LogEntryType.SNACK);
        entryIn.setCalories(-1);

        //act
        LogEntryResult result = service.create(entryIn);
        //assert
        assertFalse(result.isSuccessful());
        assertTrue(result.getMessages().contains("calories must be a positive number"));
    }

    @Test
    void shouldNotAddTooManyCalories() {
        //arrange
        LogEntry entryIn = new LogEntry();
        entryIn.setLoggedOn("2020-10-01");
        entryIn.setDescription("something"); //max length 100
        entryIn.setType(LogEntryType.SNACK);
        entryIn.setCalories(3001);

        //act
        LogEntryResult result = service.create(entryIn);
        //assert
        assertFalse(result.isSuccessful());
        assertTrue(result.getMessages().contains("calories is too high"));
    }

    @Test
    void shouldUpdate() {
        //arrange
        LogEntry entryIn = new LogEntry();
        entryIn.setId(1);
        entryIn.setLoggedOn("2020-10-01");
        entryIn.setDescription("something");
        entryIn.setType(LogEntryType.SNACK);
        entryIn.setCalories(1);

        when(repository.update(entryIn)).thenReturn(true);

        //act
        LogEntryResult result = service.update(entryIn);

        //assert
        assertTrue(result.isSuccessful());
        assertNotNull(result.getPayload());
        assertEquals(entryIn, result.getPayload());
    }

    @Test
    void shouldNotUpdateIfRepositoryFail() {
        //arrange
        LogEntry entryIn = new LogEntry();
        entryIn.setId(1);
        entryIn.setLoggedOn("2020-10-01");
        entryIn.setDescription("something");
        entryIn.setType(LogEntryType.SNACK);
        entryIn.setCalories(1);

        when(repository.update(entryIn)).thenReturn(false);

        //act
        LogEntryResult result = service.update(entryIn);

        //assert
        assertFalse(result.isSuccessful());
        assertNull(result.getPayload());
        assertEquals(1,result.getMessages().size());
        assertTrue(result.getMessages().contains("unable to update"));
    }

    @Test
    void shouldNotUpdateBadId() {
        //arrange
        LogEntry entryIn = new LogEntry();

        //act
        LogEntryResult result = service.update(entryIn);

        //assert
        assertFalse(result.isSuccessful());
        assertEquals(1,result.getMessages().size());
        assertTrue(result.getMessages().contains("id is required and must be positive"));
    }

    @Test
    void shouldDelete() {
        //arrange
        when(service.deleteById(1)).thenReturn(true);

        //act
        boolean result = service.deleteById(1);

        //assert
        assertTrue(result);
    }

    @Test
    void shouldNotDeleteNegativeId() {
        //act
        boolean result = service.deleteById(-1);

        //assert
        assertFalse(result);
    }

    @Test
    void shouldNotDeleteIfRepositoryFail() {
        //arrange
        when(service.deleteById(1)).thenReturn(false);

        //act
        boolean result = service.deleteById(1);

        //assert
        assertFalse(result);
    }
}