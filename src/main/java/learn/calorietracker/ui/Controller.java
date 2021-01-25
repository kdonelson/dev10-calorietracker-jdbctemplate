package learn.calorietracker.ui;

import learn.calorietracker.data.DataAccessException;
import learn.calorietracker.data.LogEntryFileRepository;
import learn.calorietracker.data.LogEntryRepository;
import learn.calorietracker.domain.LogEntryResult;
import learn.calorietracker.domain.LogEntryService;
import learn.calorietracker.models.LogEntry;
import org.springframework.stereotype.Component;

import java.util.List;

// TODO display a list of entries

@Component
public class Controller {

    LogEntryService service;
    View view;

    public Controller(LogEntryService service, View view) {
        this.service = service;
        this.view = view;
    }

    public void run() {

        try {
            // TODO displaying a menu

            // TODO if the user selecting menu item option #1, display the list

//            // Anti-pattern #1: the UI layer should not directly access the data layer
//
//            LogEntryFileRepository repository = new LogEntryFileRepository("data/log-entries.csv");
//            List<LogEntry> entries = repository.findAll();
//
//            for (LogEntry entry : entries) {
//                System.out.println(entry);
//            }

            // user selects menu option to view entries...
            displayEntries();

            // user selects menu option to create an entry...
            createEntry();

            // user selects menu option to view entries...
            displayEntries();
        } catch (Exception ex) {
            // TODO log the exception
            System.out.println("Oops! Something unexpected happened. Please contact IT support for help.");
        }

    }

    private void displayEntries() throws DataAccessException {
        List<LogEntry> entries = service.findAll();

        // MVC Rule #1: In the controller, never use System.out or System.in
        view.displayEntries(entries);
    }

    private void createEntry() throws DataAccessException {
        // get a log entry
        // ask the user for input
        LogEntry entry = view.getLogEntry();

        // call the service to create the log entry
        LogEntryResult result = service.create(entry);

        // display the result to the user
        if (result.isSuccessful()) {
            // successful (no messages to display)
            view.displayMessage("Log entry #%s was created!", result.getPayload().getId());
        } else {
            // failed (we have messages to display)
            view.displayErrorMessages(result.getMessages());
        }
    }
}
