package pl.piotrowski.remotetexteditor.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import pl.piotrowski.remotetexteditor.Application;
import pl.piotrowski.remotetexteditor.dataaccess.DocumentsDAO;
import pl.piotrowski.remotetexteditor.model.Document;
import pl.piotrowski.remotetexteditor.service.DocumentsService;
import pl.piotrowski.remotetexteditor.test.configuration.TestContext;

import java.util.HashSet;
import java.util.function.Supplier;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {TestContext.class, Application.class})
public class DocumentsServiceTest {

    @Autowired
    DocumentsService documentsService;
    @Autowired
    Supplier<Document> testDocumentFactory;
    @MockBean
    DocumentsDAO documentsDAO;

    @Before
    public void setup(){
        Document document = testDocumentFactory.get();
    }

    @Test
    public void addDocumentTest() {

    }
}
