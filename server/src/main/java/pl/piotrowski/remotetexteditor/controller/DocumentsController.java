package pl.piotrowski.remotetexteditor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.piotrowski.remotetexteditor.application.DocumentsService;
import pl.piotrowski.remotetexteditor.model.Document;
import pl.piotrowski.remotetexteditor.model.Update;
import pl.piotrowski.remotetexteditor.service.exceptions.DocumentAlreadyExistsException;
import pl.piotrowski.remotetexteditor.service.exceptions.DocumentNotFoundException;

import java.util.HashMap;
import java.util.HashSet;


@Controller
@RequestMapping("/docs")
@CrossOrigin
public class DocumentsController {

    private final DocumentsService documentsService;

    @Autowired
    public DocumentsController(DocumentsService documentsService) {
        this.documentsService = documentsService;
    }

    @MessageMapping("/update/{name}")
    @SendTo("/topic/updates/{name}")
    public Update updateDocumentsContent(@DestinationVariable String name, @Payload Update update) {

        tryUpdate(name, update);


        return update;
    }

    private void tryUpdate(String name, Update update) {
        try {
            documentsService.updateDocumentsContent(name, update);
        } catch (DocumentNotFoundException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<Document> getDocument(@PathVariable String name) {
        try {
            return ResponseEntity.ok().body(documentsService.getDocument(name));
        } catch (DocumentNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(404).build();
        }
    }


    @PostMapping
    public ResponseEntity<Document> createDocument(@RequestBody Document document) {
        try {
            document = documentsService.addDocument(document);
        } catch (DocumentAlreadyExistsException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(document);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<HashMap<String, Document>> deleteDocument(@PathVariable String name) {
        HashMap<String, Document> documents = new HashMap<>();

        try {
            documents.put("deleted", documentsService.getDocument(name));
            documentsService.removeDocument(name);
        } catch (DocumentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(documents);
    }

    @GetMapping
    public ResponseEntity<HashSet<Document>> getDocuments() {
        return ResponseEntity.ok(documentsService.getAllDocuments());
    }

    @PatchMapping("/{name}")
    public ResponseEntity<String> renameDocument(@PathVariable String name, @RequestParam String newName) {
        try {
            String string = documentsService.changeDocumentsName(name, newName).getName();
            return ResponseEntity.ok(string);
        } catch (DocumentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }
}
