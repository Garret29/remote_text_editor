package pl.piotrowski.remotetexteditor.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.piotrowski.remotetexteditor.Application;
import pl.piotrowski.remotetexteditor.configuration.TestContext;
import pl.piotrowski.remotetexteditor.dataaccess.DocumentsRepository;
import pl.piotrowski.remotetexteditor.model.Document;
import pl.piotrowski.remotetexteditor.model.Update;
import pl.piotrowski.remotetexteditor.service.exceptions.DocumentNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestContext.class, Application.class})
@ActiveProfiles("test")
class DocumentsServiceTest {

    @Autowired
    private DocumentsService documentsService;
    @Autowired
    private Supplier<Document> testDocumentFactory;
    @Autowired
    private Supplier<HashSet<Document>> testDocumentSetFactory;
    @Autowired
    private Supplier<Update> testUpdateFactory;

    @MockBean
    private DocumentsRepository documentsRepository;

    private Document document;
    private HashSet<Document> documentHashSet;

    @BeforeEach
    void setup() {
        document = testDocumentFactory.get();
        documentHashSet = testDocumentSetFactory.get();
    }


    @Test
    void getDocumentTest() throws Exception {
        given(documentsRepository.findByName(document.getName())).willReturn(Optional.of(document));
        Document found = documentsService.getDocument(document.getName());
        then(documentsRepository).should().findByName(document.getName());
        assertEquals(found, document);
    }

    @Test
    void addDocumentTest() {
        given(documentsRepository.save(document)).willReturn(document);
        Document added = documentsService.addDocument(document);
        then(documentsRepository).should().save(document);
        assertEquals(document, added);
    }

    @Test
    void removeDocumentTest() throws Exception {
        willDoNothing().given(documentsRepository).deleteByName(document.getName());

        documentsService.removeDocument(document.getName());

        then(documentsRepository).should().deleteByName(document.getName());
    }

    @Test
    void updateDocumentsContentTest() throws Exception {
        Update update = testUpdateFactory.get();

        given(documentsRepository.save(document)).willReturn(document);
        given(documentsRepository.findByName(document.getName())).willReturn(Optional.of(document));
        Document updated = documentsService.updateDocumentsContent(document.getName(), update);
        document.applyUpdate(update);
        then(documentsRepository).should().save(document);
        assertEquals(updated, document);
    }

    @Test
    void getAllDocumentsTest() {
        given(documentsRepository.getAll()).willReturn(documentHashSet.stream());
        HashSet<Document> found = documentsService.getAllDocuments();
        then(documentsRepository).should().getAll();
        assertEquals(documentHashSet, found);
    }
}
