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
        return ResponseEntity.ok().body(documentsService.getDocument(name));
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
        documentsService.addDocument(document);

        return ResponseEntity.ok(document);
    }

    @Override
    @DeleteMapping("/{name}/delete")
    public ResponseEntity<HashMap<String, Document>> deleteDocument(@PathVariable String name) {
        HashMap<String, Document> documents = new HashMap<>();

        documents.put("deleted", documentsService.getDocument(name));
        documentsService.removeDocument(name);

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
        documentsService.changeDocumentsName(name, newName);
        return ResponseEntity.ok().build();
    }
}
