package pl.piotrowski.remotetexteditor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import pl.piotrowski.remotetexteditor.dataaccess.DocumentsRepository;
import pl.piotrowski.remotetexteditor.model.Document;

@SpringBootApplication
@ComponentScan(basePackages = "pl.piotrowski.remotetexteditor")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(DocumentsRepository documentsRepository) {
        return args -> {
            documentsRepository.save(new Document("Hello", "World"));
            documentsRepository.save(new Document("Hi", "there"));
        };
    }
}