package pl.piotrowski.remotetexteditor.dataaccess;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.piotrowski.remotetexteditor.Application;
import pl.piotrowski.remotetexteditor.application.DocumentsService;
import pl.piotrowski.remotetexteditor.configuration.TestContext;
import pl.piotrowski.remotetexteditor.model.Document;
import pl.piotrowski.remotetexteditor.service.exceptions.DocumentNotFoundException;

import javax.persistence.PersistenceException;
import javax.print.Doc;
import java.util.HashSet;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = {TestContext.class, Application.class})
public class DataAccessIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private DocumentsRepository documentsRepository;
    @Autowired
    private Supplier<Document> testDocumentFactory;
    @Autowired
    private Supplier<HashSet<Document>> testDocumentsSetFactory;

    private Document document;
    private HashSet<Document> documents;

    @BeforeEach
    void setup() {
        document = testDocumentFactory.get();
        documents = testDocumentsSetFactory.get();

        entityManager.persist(document);
        documents.forEach(document -> entityManager.persist(document));
        entityManager.flush();
    }

    @Test
    public void findByNameTest() {
        Optional<Document> optionalDocument = documentsRepository.findByName(document.getName());
        Document found = optionalDocument.get();

        assertEquals(found, document);
    }

    @Test
    void getAllTest() {
        Stream<Document> documentStream = documentsRepository.getAll();

        assertEquals(documentStream.count(), documents.size() + 1);
    }

    @Test
    void deleteByNameTest() {
        documentsRepository.deleteByName(document.getName());

        Optional<Document> foundOptional = documentsRepository.findByName(document.getName());
        assertFalse(foundOptional.isPresent());

    }

    @Test
    void saveDuplicateThrowsException() {
        Document newDoc = new Document("Hello there!","General Kenobi!");
        documentsRepository.save(newDoc);
        documentsRepository.save(new Document("Hello there!", "Hi!"));
        assertThrows(PersistenceException.class, ()-> entityManager.flush());
    }
}
