package pl.piotrowski.remotetexteditor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan(basePackages = "pl.piotrowski.remotetexteditor")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
