package pl.piotrowski.remotetexteditor.controller;

import org.springframework.test.context.ContextConfiguration;
import pl.piotrowski.remotetexteditor.configuration.TestContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.piotrowski.remotetexteditor.Application;
import pl.piotrowski.remotetexteditor.application.DocumentsService;
import pl.piotrowski.remotetexteditor.model.Document;

import java.util.function.Supplier;

import static org.mockito.BDDMockito.willDoNothing;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(DocumentsController.class)
@ContextConfiguration(classes = {TestContext.class, Application.class})
public class DocumentsControllerWebSocketIntegrationTest {

    @Autowired
    Supplier<Document> testDocumentFactory;
    @MockBean
    private DocumentsService documentsService;

    @Test
    public void updateDocumentTest() throws Exception {
        Document document = testDocumentFactory.get();

        willDoNothing().given(documentsService).updateDocumentsContent(document.getName(), document.getContent());




    }
}
