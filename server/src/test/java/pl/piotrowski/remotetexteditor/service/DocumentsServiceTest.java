package pl.piotrowski.remotetexteditor.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.piotrowski.remotetexteditor.Application;
import pl.piotrowski.remotetexteditor.configuration.TestContext;
import pl.piotrowski.remotetexteditor.dataaccess.DocumentsRepository;
import pl.piotrowski.remotetexteditor.model.Document;

import java.util.HashSet;
import java.util.Optional;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, Application.class})
public class DocumentsServiceTest {

    @Autowired
    private DocumentsService documentsService;
    @Autowired
    private Supplier<Document> testDocumentFactory;
    @Autowired
    private Supplier<HashSet<Document>> testDocumentSetFactory;

    @MockBean
    private DocumentsRepository documentsRepository;

    private Document document;
    private HashSet<Document> documentHashSet;

    @Before
    public void setup() {
        document = testDocumentFactory.get();
        documentHashSet = testDocumentSetFactory.get();
    }


    @Test
    public void getDocumentTest() throws Exception {
        given(documentsRepository.getByName(document.getName())).willReturn(Optional.of(document));
        Document found = documentsService.getDocument(document.getName());
        verify(documentsRepository).getByName(document.getName());
        assertEquals(found, document);
    }

    @Test
    public void addDocumentTest() {
        given(documentsRepository.save(document)).willReturn(document);
        Document added = documentsService.addDocument(document);
        verify(documentsRepository).save(document);
        assertEquals(document, added);
    }

    @Test
    public void removeDocumentTest() throws Exception {
        given(documentsRepository.deleteByName(document.getName())).willReturn(Optional.of(document));
        Document removed = documentsService.removeDocument(document.getName());
        verify(documentsRepository).deleteByName(document.getName());
        assertEquals(removed, document);
    }

    @Test
    public void updateDocumentsContentTest() throws Exception {
        given(documentsRepository.save(document)).willReturn(document);
        given(documentsRepository.getByName(document.getName())).willReturn(Optional.of(document));
        Document updated = documentsService.updateDocumentsContent(document.getName(), "New content!");
        document.setContent("New Content!");
        verify(documentsRepository).save(document);
        assertEquals(updated, document);
    }

    @Test
    public void getAllDocumentsTest() throws Exception {
        given(documentsRepository.getAll()).willReturn(documentHashSet.stream());
        HashSet<Document> found = documentsService.getAllDocuments();
        verify(documentsRepository).getAll();
        assertEquals(documentHashSet, found);
    }
}
