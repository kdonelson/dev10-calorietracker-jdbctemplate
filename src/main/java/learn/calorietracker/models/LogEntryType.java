package learn.calorietracker.models;

public enum LogEntryType {
    BREAKFAST(1, "Breakfast"),
    LUNCH(2, "Lunch"),
    DINNER(3, "Dinner"),
    SNACK(4, "Snack"),
    SECOND_BREAKFAST(5, "Second Breakfast");

    private final int value;
    private final String type;

    LogEntryType(int value, String type) {
        this.value = value;
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public static LogEntryType findByValue(int value) {
        for (LogEntryType logEntryType : LogEntryType.values()) {
            if (logEntryType.getValue() == value) {
                return logEntryType;
            }
        }
        String message = String.format("No LogEntryType with value: %s.", value);
        throw new RuntimeException(message);
    }

    public static LogEntryType findByType(String type) {
        for (LogEntryType logEntryType : LogEntryType.values()) {
            if (logEntryType.getType().equalsIgnoreCase(type)) {
                return logEntryType;
            }
        }
        String message = String.format("No LogEntryType with type: %s.", type);
        throw new RuntimeException(message);
    }
}
