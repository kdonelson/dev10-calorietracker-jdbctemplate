package learn.calorietracker.data;

import learn.calorietracker.domain.LogEntryService;
import learn.calorietracker.models.LogEntry;
import learn.calorietracker.models.LogEntryType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LogEntryFileRepository implements LogEntryRepository {
    /*
     * TODO
     * `LogEntry create(LogEntry entry)`
     * `boolean update(LogEntry entry)`
     * `boolean deleteById(int id)`
     */

    private static final String DELIMITER = ",";

    private final String filePath;

    public LogEntryFileRepository(@Value("${dataFilePath:./data/log-entries.csv}") String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<LogEntry> findAll() throws DataAccessException {
        ArrayList<LogEntry> result = new ArrayList<>();

        // read through the csv file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            // skip past the first line that contains headers
            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                // 1,2020-01-01 09:00 AM,BREAKFAST,Scrambled eggs,210
                String[] fields = line.split(DELIMITER);

                if (fields.length == 5) {
                    LogEntry entry = new LogEntry(
                            Integer.parseInt(fields[0]), // 0
                            fields[1], // 1
                            LogEntryType.valueOf(fields[2]), // 2
                            fields[3], // 3
                            Integer.parseInt(fields[4]) // 4
                    );
                    result.add(entry);
                }
            }
        } catch (FileNotFoundException e) {
            // do nothing... the file will get created when adding the first record
        } catch (IOException e) {
            // log the exception
            // rethrow
            e.printStackTrace(); // another file where we log data access exceptions
            throw new DataAccessException("A data access exception occurred.", e);
        }

        return result;
    }

    @Override
    public List<LogEntry> findByType(LogEntryType type) throws DataAccessException {
        ArrayList<LogEntry> results = new ArrayList<>();

        List<LogEntry> entries = findAll();

        for (LogEntry entry : entries) {
            if (entry.getType() == type) {
                results.add(entry);
            }
        }

        return results;
    }

    @Override
    public LogEntry findById(int id) throws DataAccessException {
        List<LogEntry> entries = findAll();

        for (LogEntry entry : entries) {
            if (entry.getId() == id) {
                return entry;
            }
        }

        // if we don't find a record, return null
        return null;
    }

    @Override
    public LogEntry create(LogEntry entry) throws DataAccessException {
        List<LogEntry> entries = findAll();
        entry.setId(getNextId(entries));
        entries.add(entry);
        writeToFile(entries);
        return entry;
    }

    private void writeToFile(List<LogEntry> entries) throws DataAccessException {
        // try-with-resources
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println("id,loggedOn,type,description,calories");
            for (LogEntry entry : entries) {
                writer.println(entryToLine(entry));
            }
        } catch (IOException ex) {
            // Why might we not be able to write to the file?
            // disk full
            // file read only
            // permissions on the directory
            // bogus file path
            // lock on the file
            throw new DataAccessException("Could not write to file path: " + filePath, ex);
        }
    }

    private String entryToLine(LogEntry entry) {
        // 1,2020-01-01 09:00 AM,BREAKFAST,Scrambled eggs,210

//        String result = "";
//        result += entry.getId() + DELIMITER;
//        result += entry.getLoggedOn() + DELIMITER;
//        result += entry.getType() + DELIMITER;
//        result += entry.getDescription() + DELIMITER;
//        result += entry.getCalories();
//        return result;

        StringBuilder sb = new StringBuilder();
        sb.append(entry.getId()).append(DELIMITER);
        sb.append(entry.getLoggedOn()).append(DELIMITER);
        sb.append(entry.getType()).append(DELIMITER);
        sb.append(entry.getDescription()).append(DELIMITER);
        sb.append(entry.getCalories());
        return sb.toString();

//        return String.format("%s%s%s%s%s",
//                entry.getId() + DELIMITER,
//                entry.getLoggedOn() + DELIMITER,
//                entry.getType() + DELIMITER,
//                entry.getDescription() + DELIMITER,
//                entry.getCalories());
    }

    private int getNextId(List<LogEntry> entries) {
        int maxId = 0;
        for (LogEntry entry : entries) {
            if (maxId < entry.getId()) {
                maxId = entry.getId();
            }
        }
        return maxId + 1;
    }
}
