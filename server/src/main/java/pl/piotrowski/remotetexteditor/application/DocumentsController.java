package pl.piotrowski.remotetexteditor.application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface DocumentsController {
    ResponseEntity<?> updateDocumentsContent(String name, String content);
    ResponseEntity<?> createDocument(String name, String content);
    ResponseEntity<?> deleteDocument(String name);
    ResponseEntity<?> getDocument(String name);
    ResponseEntity<?> getDocuments();
    ResponseEntity<?> renameDocument(String name, String newName);
}