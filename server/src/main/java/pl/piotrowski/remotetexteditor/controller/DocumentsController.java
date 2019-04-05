package pl.piotrowski.remotetexteditor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.piotrowski.remotetexteditor.model.Document;
import pl.piotrowski.remotetexteditor.model.Editable;
import pl.piotrowski.remotetexteditor.application.DocumentsService;
import pl.piotrowski.remotetexteditor.service.exceptions.DocumentAlreadyExistsException;
import pl.piotrowski.remotetexteditor.service.exceptions.DocumentNotFoundException;

import java.util.HashMap;
import java.util.HashSet;


@Controller
@RequestMapping("/docs")
public class DocumentsController implements pl.piotrowski.remotetexteditor.application.DocumentsController {

    private DocumentsService documentsService;

    @Autowired
    public DocumentsController(DocumentsService documentsService) {
        this.documentsService = documentsService;
    }

    @Override
    @GetMapping("/{name}")
    public ResponseEntity<Document> getDocument(@PathVariable String name) {
        try {
            return ResponseEntity.ok().body(documentsService.getDocument(name));
        } catch (DocumentNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @Override
    @MessageMapping("/{name}")
    @SendTo("/topic/updates")
    public ResponseEntity<String> updateDocumentsContent(@PathVariable String name, @RequestBody String content) {
        return null;
    }

    @Override
    @PostMapping
    public ResponseEntity<Document> createDocument(@RequestParam String name, @RequestParam(defaultValue = "") String content) {
        Document document = new Document(name, content);
        try {
            documentsService.addDocument(document);
        } catch (DocumentAlreadyExistsException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(document);
    }

    @Override
    @DeleteMapping("/{name}/delete")
    public ResponseEntity<HashMap<String, Document>> deleteDocument(@PathVariable String name) {
        HashMap<String, Document> documents = new HashMap<>();

        try {
            documents.put("deleted", documentsService.getDocument(name));
        } catch (DocumentNotFoundException e) {
            e.printStackTrace();
        }
        try {
            documentsService.removeDocument(name);
        } catch (DocumentNotFoundException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(documents);
    }

    @Override
    @GetMapping
    public ResponseEntity<HashSet<Document>> getDocuments() {
        return ResponseEntity.ok(documentsService.getAllDocuments());
    }

    @Override
    @PatchMapping("/{name}")
    public ResponseEntity<?> renameDocument(@PathVariable String name, @RequestParam String newName) {
        try {
            documentsService.changeDocumentsName(name, newName);
        } catch (DocumentNotFoundException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }
}
