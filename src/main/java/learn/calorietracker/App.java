package learn.calorietracker;

import learn.calorietracker.data.LogEntryFileRepository;
import learn.calorietracker.domain.LogEntryService;
import learn.calorietracker.models.LogEntry;
import learn.calorietracker.ui.Controller;
import learn.calorietracker.ui.View;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@ComponentScan
@PropertySource("classpath:data.properties")
public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(App.class);

        Controller controller = context.getBean(Controller.class);
        controller.run();
    }
}
