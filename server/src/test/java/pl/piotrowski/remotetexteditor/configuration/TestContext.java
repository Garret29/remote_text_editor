package pl.piotrowski.remotetexteditor.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import pl.piotrowski.remotetexteditor.dataaccess.DocumentsRepository;
import pl.piotrowski.remotetexteditor.model.Document;

import java.util.HashSet;
import java.util.Random;
import java.util.function.Supplier;

@TestConfiguration
public class TestContext {
    @Bean
    public Supplier<Document> testDocumentFactory(){
        return () -> {
            String name = "HW";
            String content = "Hello World";

            return new Document(name, content);
        };
    }

    @Bean
    public Supplier<HashSet<Document>> testDocumentSetFactory(){
        return ()->{
            HashSet<Document> documents = new HashSet<>();
            Random random = new Random();
            for(int i=0; i<20; i++){
                Document document = new Document("HW"+i,  Long.toString(random.nextLong()));
                documents.add(document);
            }
            return documents;
        };
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

}
