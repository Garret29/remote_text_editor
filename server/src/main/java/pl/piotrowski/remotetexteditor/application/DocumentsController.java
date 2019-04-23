package pl.piotrowski.remotetexteditor.application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import pl.piotrowski.remotetexteditor.model.Document;
import pl.piotrowski.remotetexteditor.model.Update;

public interface DocumentsController {
    Update updateDocumentsContent(String name, Update update
//            , int start, boolean isReplacing
    );
    ResponseEntity<?> createDocument(Document document);
    ResponseEntity<?> deleteDocument(String name);
    ResponseEntity<?> getDocument(String name);
    ResponseEntity<?> getDocuments();
    ResponseEntity<?> renameDocument(String name, String newName);
}