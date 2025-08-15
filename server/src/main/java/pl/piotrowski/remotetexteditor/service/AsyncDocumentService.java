package pl.piotrowski.remotetexteditor.service;

import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.piotrowski.remotetexteditor.application.DocumentsService;
import pl.piotrowski.remotetexteditor.model.Document;
import pl.piotrowski.remotetexteditor.model.Update;
import pl.piotrowski.remotetexteditor.service.exceptions.DocumentAlreadyExistsException;
import pl.piotrowski.remotetexteditor.service.exceptions.DocumentNotFoundException;

import java.util.HashSet;

@Service
@Primary
public class AsyncDocumentService implements DocumentsService {

    private final DocumentsService delegate;

    public AsyncDocumentService(DocumentsService delegate) {
        this.delegate = delegate;
    }

    @Override
    public Document addDocument(Document document) throws DocumentAlreadyExistsException {
        return delegate.addDocument(document);
    }

    @Override
    public void removeDocument(String name) throws DocumentNotFoundException {
        delegate.removeDocument(name);
    }

    @Override
    public Document changeDocumentsName(String oldName, String newName) throws DocumentNotFoundException {
        return delegate.changeDocumentsName(oldName, newName);
    }

    @Async
    @Override
    public Document updateDocumentsContent(String name, Update update) throws DocumentNotFoundException {
        return delegate.updateDocumentsContent(name, update);
    }

    @Override
    public Document getDocument(String name) throws DocumentNotFoundException {
        return delegate.getDocument(name);
    }

    @Override
    public HashSet<Document> getAllDocuments() {
        return delegate.getAllDocuments();
    }
}
