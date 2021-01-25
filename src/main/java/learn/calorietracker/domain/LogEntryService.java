package learn.calorietracker.domain;

import learn.calorietracker.data.DataAccessException;
import learn.calorietracker.data.LogEntryFileRepository;
import learn.calorietracker.data.LogEntryRepository;
import learn.calorietracker.models.LogEntry;
import learn.calorietracker.models.LogEntryType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogEntryService {
    private LogEntryRepository repository;

    public LogEntryService(LogEntryRepository repository) {
        this.repository = repository;
    }

    public List<LogEntry> findAll() throws DataAccessException {
        return repository.findAll();
    }

    public List<LogEntry> findByType(LogEntryType type) throws DataAccessException {
        return repository.findByType(type);
    }

    public LogEntry findById(int id) throws DataAccessException {
        return repository.findById(id);
    }

    public LogEntryResult create(LogEntry entry) throws DataAccessException {

        /*
         * TODO Validations to write:
         * Required values
         * Positive number for calories
         * Max calories???
         * Max description length???
         * Restrict how many entries per type for a single day???
         */

        // loggedOn 2020-01-01
        // type BREAKFAST

        // loggedOn 2020-01-02
        // type BREAKFAST

//        if (entry.getType() == LogEntryType.BREAKFAST) {
//            // TODO go get all of the entries for this day that are of type "BREAKFAST"
//            // TODO do we have too many?
//        }

        LogEntry newLogEntry = repository.create(entry);

        LogEntryResult result = new LogEntryResult();
        result.setPayload(newLogEntry);

        return result;
    }

    public LogEntryResult update(LogEntry entry) {
        return null;
    }

    public LogEntryResult deleteById(int id) {
        return null;
    }
}
