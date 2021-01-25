package learn.calorietracker.ui;

import learn.calorietracker.models.LogEntry;
import learn.calorietracker.models.LogEntryType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class View {
    Scanner console = new Scanner(System.in);

    public void displayEntries(List<LogEntry> entries) {
        for (LogEntry entry : entries) {
            System.out.println(entry);
        }
    }

    public LogEntry getLogEntry() {
        LogEntry entry = new LogEntry();

        entry.setLoggedOn(getRequiredString("Enter a logged on date [2020-01-01 09:00 AM]: "));
        // TODO write helper method to get the type from the user
        entry.setType(LogEntryType.BREAKFAST);
        entry.setDescription(getRequiredString("Enter a description: "));
        // TODO write the helper method to get an int with min and max from the user
        entry.setCalories(100);

        return entry;
    }

//    public void displayMessage(String message) {
//        System.out.println(message);
//    }

    // totally optional
    // Varargs
    // "message", arg1, arg2, arg3, arg4, ...
    public void displayMessage(String message, Object... args) {
        System.out.println(String.format(message, args));
    }

    public void displayErrorMessages(List<String> messages) {
        System.out.println("[Errors]");
        for (String message : messages) {
            System.out.println(message);
        }
    }

    // private methods

    private String getString(String prompt) {
        System.out.print(prompt);
        return console.nextLine();
    }

    private String getRequiredString(String prompt) {
        String value = null;

        do {
            value = getString(prompt);
            if (value == null || value.isEmpty()) {
                System.out.println("Please provide a value.");
            }
        } while (value == null || value.isEmpty());

        return value;
    }
}
