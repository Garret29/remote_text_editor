package pl.piotrowski.remotetexteditor.controller;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.piotrowski.remotetexteditor.Application;
import pl.piotrowski.remotetexteditor.application.DocumentsService;
import pl.piotrowski.remotetexteditor.configuration.TestContext;
import pl.piotrowski.remotetexteditor.model.Document;

import java.util.function.Supplier;

import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DocumentsController.class)
@ContextConfiguration(classes = {TestContext.class, Application.class})
class DocumentsControllerWebSocketIntegrationTest {

    @Autowired
    Supplier<Document> testDocumentFactory;
    @MockBean
    private DocumentsService documentsService;

    @Test
    @Disabled
    void updateDocumentTest() throws Exception {
        Document document = testDocumentFactory.get();

        willDoNothing().given(documentsService).updateDocumentsContent(document.getName(), document.getContent());




    }
}
