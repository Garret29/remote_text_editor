package pl.piotrowski.remotetexteditor.application;

import pl.piotrowski.remotetexteditor.model.Document;

import java.util.HashSet;

public interface DocumentsService {
    void addDocument(Document document);

    void removeDocument(String name);

    void changeDocumentsName(String oldName, String newName);

    void updateDocument(String name, String newContent);

    Document getDocument(String name);

    HashSet<Document> getAllDocuments();
}
