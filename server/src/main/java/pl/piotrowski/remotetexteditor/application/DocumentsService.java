package pl.piotrowski.remotetexteditor.application;

import pl.piotrowski.remotetexteditor.model.Document;
import pl.piotrowski.remotetexteditor.service.exceptions.DocumentAlreadyExistsException;
import pl.piotrowski.remotetexteditor.service.exceptions.DocumentNotFoundException;

import java.util.HashSet;

public interface DocumentsService {
    Document addDocument(Document document) throws DocumentAlreadyExistsException;

    void removeDocument(String name) throws DocumentNotFoundException;

    Document changeDocumentsName(String oldName, String newName) throws DocumentNotFoundException;

    Document updateDocumentsContent(String name, String newContent) throws DocumentNotFoundException;

    Document getDocument(String name) throws DocumentNotFoundException;

    HashSet<Document> getAllDocuments();
}
