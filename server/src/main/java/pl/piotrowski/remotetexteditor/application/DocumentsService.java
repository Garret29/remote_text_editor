package pl.piotrowski.remotetexteditor.application;

public interface DocumentsService {
void addDocument(String name);
void removeDocument(String name);
void changeDocumentsName(String oldName, String newName);
void updateDocument(String name, String newContent);
}
