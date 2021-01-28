package learn.calorietracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.calorietracker.domain.LogEntryResult;
import learn.calorietracker.domain.LogEntryService;
import learn.calorietracker.models.LogEntry;
import learn.calorietracker.models.LogEntryType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LogEntryControllerTest {

    @MockBean
    LogEntryService service;

    @Autowired
    MockMvc mvc;

    @Test
    void shouldGetAll() throws Exception {
        //arrange
        List<LogEntry> entries = List.of(
             new LogEntry(1, "2020-01-01", LogEntryType.BREAKFAST, "Coffee", 10),
                new LogEntry(1, "2020-01-01", LogEntryType.LUNCH, "Salad", 20),
                new LogEntry(1, "2020-01-01", LogEntryType.DINNER, "Tacos", 30)
        );

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(entries);

        when(service.findAll()).thenReturn(entries);

        mvc.perform(get("/log"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldNotGetByNegativeLogEntryId() throws Exception {
        mvc.perform(get("/log/-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotGetWhenCantFindId() throws Exception {
        when(service.findById(1)).thenReturn(null);

        mvc.perform(get("/log/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetById() throws Exception {
        LogEntry entry = new LogEntry(1, "2020-01-01", LogEntryType.BREAKFAST, "Coffee", 10);

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(entry);

        when(service.findById(1)).thenReturn(entry);

        mvc.perform(get("/log/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldAdd() throws Exception {
        LogEntry entryIn = new LogEntry(0, "2020-01-01", LogEntryType.BREAKFAST, "Coffee", 10);
        LogEntryResult expected = new LogEntryResult();
        expected.setPayload(new LogEntry(1, "2020-01-01", LogEntryType.BREAKFAST, "Coffee", 10));

        when(service.create(any())).thenReturn(expected);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(entryIn);
        String expectedJson = jsonMapper.writeValueAsString(expected);

        var request = post("/log")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldNotAddIfServiceFails() throws Exception {
        LogEntry entryIn = new LogEntry(0, "2020-01-01", LogEntryType.BREAKFAST, "Coffee", 10);
        LogEntryResult expected = new LogEntryResult();
        expected.addMessage("It didn't work");

        when(service.create(any())).thenReturn(expected);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(entryIn);
        String expectedJson = jsonMapper.writeValueAsString(expected);

        var request = post("/log")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldUpdate() throws Exception {
        LogEntry entryIn = new LogEntry(0, "2020-01-01", LogEntryType.BREAKFAST, "Coffee", 10);
        LogEntryResult expected = new LogEntryResult();
        expected.setPayload(new LogEntry(1, "2020-01-01", LogEntryType.BREAKFAST, "Coffee", 10));

        when(service.update(any())).thenReturn(expected);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(entryIn);
        String expectedJson = jsonMapper.writeValueAsString(expected);

        var request = put("/log/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldNotUpdateIfServiceFails() throws Exception {
        LogEntry entryIn = new LogEntry(0, "2020-01-01", LogEntryType.BREAKFAST, "Coffee", 10);
        LogEntryResult expected = new LogEntryResult();
        expected.addMessage("It didn't work");

        when(service.update(any())).thenReturn(expected);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(entryIn);
        String expectedJson = jsonMapper.writeValueAsString(expected);

        var request = put("/log/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldDelete() throws Exception{
        when(service.deleteById(1)).thenReturn(true);

        mvc.perform(delete("/log/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotDelete() throws Exception{
        when(service.deleteById(1)).thenReturn(false);

        mvc.perform(delete("/log/1"))
                .andExpect(status().isNotFound());
    }
}
