package learn.calorietracker.domain;

import learn.calorietracker.data.LogEntryRepository;
import learn.calorietracker.models.LogEntry;
import learn.calorietracker.models.LogEntryType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LogEntryService {
    private LogEntryRepository repository;

    public LogEntryService(LogEntryRepository repository) {
        this.repository = repository;
    }

    public List<LogEntry> findAll() {
        return repository.findAll();
    }

    public List<LogEntry> findByType(LogEntryType type) {
        return repository.findByType(type);
    }

    public LogEntry findById(int id){
        return repository.findById(id);
    }

    public LogEntryResult create(LogEntry entry){
        LogEntryResult result = validate(entry, true);
        /*
         * TODO Validations to write:
         * Restrict how many entries per type for a single day???
         */

        // loggedOn 2020-01-01
        // type BREAKFAST

        // loggedOn 2020-01-02
        // type BREAKFAST
        if (!result.isSuccessful()) {
            return result;
        }

        LogEntry newLogEntry = repository.create(entry);


        result.setPayload(newLogEntry);

        return result;
    }

    public LogEntryResult update(LogEntry entry) {
        LogEntryResult result = validate(entry, false);

        if (result.isSuccessful()) {
            boolean success = repository.update(entry);

            if (success) {
                result.setPayload(entry);
            } else {
                result.addMessage("unable to update");
            }
        }

        return result;
    }

    public boolean deleteById(int id) {
        if (id <= 0) {
            return false;
        }

        return repository.delete(id);
    }

    private LogEntryResult validate(LogEntry entry, boolean isNewEntry) {
        LogEntryResult result = new LogEntryResult();

        if (entry == null) {
            result.addMessage("entry cannot be null");
            return result;
        }

        if (isNewEntry) {
            if (entry.getId() > 0) {
                result.addMessage("id is auto-generated and cannot be provided");
                return result;
            }
        } else {
            if (entry.getId() <= 0) {
                result.addMessage("id is required and must be positive");
                return result;
            }
        }

        if (entry.getCalories() > 3000) {
            result.addMessage("calories is too high");
        }

        if (entry.getCalories() <= 0) {
            result.addMessage("calories must be a positive number");
        }

        if (entry.getDescription() == null || entry.getDescription().isEmpty()) {
            result.addMessage("description is required");
        } else if (entry.getDescription().length() > 100) {
            result.addMessage("description must be less than or equal to 100 characters");
        }

        if (entry.getLoggedOn() == null || entry.getLoggedOn().isEmpty()) {
            result.addMessage("logged date is required");
        }

        return result;
    }
}
