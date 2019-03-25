package pl.piotrowski.remotetexteditor.application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface DocumentsController {
    public ResponseEntity<?> updateDocument(@PathVariable String document, @RequestBody String content);
}