package learn.calorietracker.data;

import learn.calorietracker.models.LogEntry;
import learn.calorietracker.models.LogEntryType;

import java.util.List;

public interface LogEntryRepository {
    List<LogEntry> findAll();

    List<LogEntry> findByType(LogEntryType type);

    LogEntry findById(int id);

    LogEntry create(LogEntry entry);

    boolean update(LogEntry entry);

    boolean delete(int id);
}
