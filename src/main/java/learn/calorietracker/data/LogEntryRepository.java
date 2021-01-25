package learn.calorietracker.data;

import learn.calorietracker.models.LogEntry;
import learn.calorietracker.models.LogEntryType;

import java.util.List;

public interface LogEntryRepository {
    List<LogEntry> findAll() throws DataAccessException;

    List<LogEntry> findByType(LogEntryType type) throws DataAccessException;

    LogEntry findById(int id) throws DataAccessException;

    LogEntry create(LogEntry entry) throws DataAccessException;
}
