package learn.calorietracker.data;

import learn.calorietracker.models.LogEntry;
import learn.calorietracker.models.LogEntryType;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class LogEntryJdbcTemplateRepository implements LogEntryRepository {

    private final JdbcTemplate template;

    public LogEntryJdbcTemplateRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<LogEntry> findAll() throws DataAccessException {
        final String sql = "select log_entry_id, logged_on, log_entry_type_id, description, calories from log_entry;";
        return template.query(sql, mapper);
    }

    @Override
    public List<LogEntry> findByType(LogEntryType type) throws DataAccessException {
        return null;
    }

    @Override
    public LogEntry findById(int id) throws DataAccessException {
        final String sql = "select log_entry_id, logged_on, log_entry_type_id, description, calories from log_entry where log_entry_id = ?;";
        try {
            return template.queryForObject(sql, mapper, id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public LogEntry create(LogEntry entry) throws DataAccessException {
        final String sql = "insert into log_entry(logged_on, log_entry_type_id, description, calories) values (?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entry.getLoggedOn());
            ps.setInt(2, entry.getType().getValue());
            ps.setString(3, entry.getDescription());
            ps.setInt(4, entry.getCalories());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        entry.setId(keyHolder.getKey().intValue());
        return entry;
    }

    private final RowMapper<LogEntry> mapper = ((resultSet, rowNum) -> {
        LogEntry entry = new LogEntry();
        entry.setId(resultSet.getInt("log_entry_id"));
        entry.setLoggedOn(resultSet.getString("logged_on"));
        entry.setType(LogEntryType.findByValue(resultSet.getInt("log_entry_type_id")));
        entry.setDescription(resultSet.getString("description"));
        entry.setCalories(resultSet.getInt("calories"));
        return entry;
    });
}
