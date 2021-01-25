package learn.calorietracker.data.doubles;

import learn.calorietracker.data.DataAccessException;
import learn.calorietracker.data.LogEntryRepository;
import learn.calorietracker.models.LogEntry;
import learn.calorietracker.models.LogEntryType;

import java.util.ArrayList;
import java.util.List;

public class LogEntryRepositoryDouble implements LogEntryRepository {
    private ArrayList<LogEntry> entries = new ArrayList<>();

    public LogEntryRepositoryDouble() {
        // Data copied from test CSV.
        entries.add(new LogEntry(1, "2020-01-01 09:00 AM", LogEntryType.BREAKFAST, "Scrambled eggs", 210));
        entries.add(new LogEntry(2, "2020-01-01 12:00 PM", LogEntryType.LUNCH, "Tuna fish salad", 500));
        entries.add(new LogEntry(3, "2020-01-01 6:00 PM", LogEntryType.DINNER, "Steak", 1000));
    }

    @Override
    public List<LogEntry> findAll() throws DataAccessException {
        return entries;
    }

    @Override
    public List<LogEntry> findByType(LogEntryType type) throws DataAccessException {
        return null;
    }

    @Override
    public LogEntry findById(int id) throws DataAccessException {
        return null;
    }

    @Override
    public LogEntry create(LogEntry entry) throws DataAccessException {
        return null;
    }
}
