package learn.calorietracker.controllers;

import learn.calorietracker.domain.LogEntryResult;
import learn.calorietracker.domain.LogEntryService;
import learn.calorietracker.models.LogEntry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/log")
public class LogEntryController {
    private final LogEntryService service;

    public LogEntryController(LogEntryService service) {
        this.service = service;
    }

    @GetMapping
    public List<LogEntry> findAll() {
        return service.findAll();
    }

    @GetMapping("/{logEntryId}")
    public ResponseEntity findById(@PathVariable int logEntryId) {
        if (logEntryId <= 0) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        LogEntry entry = service.findById(logEntryId);
        if (entry == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entry, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LogEntryResult> add(@RequestBody LogEntry entry) {
        LogEntryResult result = service.create(entry);
        if (!result.isSuccessful()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{logEntryId}")
    public ResponseEntity<LogEntryResult> update(@PathVariable int logEntryId, @RequestBody LogEntry entry) {
        entry.setId(logEntryId);
        LogEntryResult result = service.update(entry);

        if(!result.isSuccessful()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{logEntryId}")
    public ResponseEntity<Void> delete(@PathVariable int logEntryId) {
        if (service.deleteById(logEntryId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
