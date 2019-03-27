package pl.piotrowski.remotetexteditor.application;

import java.util.HashSet;
import java.util.List;

public interface DocumentsService {
    void addDocument(String name);
    void removeDocument(String name);
    void changeDocumentsName(String oldName, String newName);
    void updateDocument(String name, String newContent);
    Document getDocument(String name);
    HashSet<Document> getAllDocuments();
}
