package pl.piotrowski.remotetexteditor.application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface DocumentsController {
    ResponseEntity<?> updateDocument(String document, String content);
    ResponseEntity<?> createDocument(String name);
    ResponseEntity<?> deleteDocument(String name);
    ResponseEntity<?> getDocument(String name);
    ResponseEntity<?> getDocuments();
}