package learn.calorietracker.domain;

import learn.calorietracker.models.LogEntry;

import java.util.ArrayList;
import java.util.List;

public class LogEntryResult {
    // TODO implement this class

    private LogEntry payload;

    public LogEntry getPayload() {
        return payload;
    }

    public void setPayload(LogEntry payload) {
        this.payload = payload;
    }

    public boolean isSuccessful() {
        return true;
    }

    public List<String> getMessages() {
        return new ArrayList<>(List.of("Same error message", "Another same error message"));
    }
}
