package learn.calorietracker.models;

public class LogEntry {
    private int id;
    private String loggedOn; // "2020-01-01 09:00 AM"
    private LogEntryType type; // "Breakfast, Snack, Lunch, Dinner, Second Breakfast"
    private String description;
    private int calories;

    public LogEntry() {
    }

    public LogEntry(int id, String loggedOn, LogEntryType type, String description, int calories) {
        this.id = id;
        this.loggedOn = loggedOn;
        this.type = type;
        this.description = description;
        this.calories = calories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoggedOn() {
        return loggedOn;
    }

    public void setLoggedOn(String loggedOn) {
        this.loggedOn = loggedOn;
    }

    public LogEntryType getType() {
        return type;
    }

    public void setType(LogEntryType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "id=" + id +
                ", loggedOn='" + loggedOn + '\'' +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
