package learn.calorietracker.domain;

import learn.calorietracker.models.LogEntry;

import java.util.ArrayList;
import java.util.List;

public class LogEntryResult {
    public LogEntryResult() {
        messages = new ArrayList<>();
    }
    private List<String> messages;

    private LogEntry payload;

    public LogEntry getPayload() {
        return payload;
    }

    public void setPayload(LogEntry payload) {
        this.payload = payload;
    }

    public boolean isSuccessful() {
        return messages.isEmpty();
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    public void addMessage(String message) {
        messages.add(message);
    }
}
