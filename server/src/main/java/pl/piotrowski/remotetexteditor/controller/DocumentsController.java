package pl.piotrowski.remotetexteditor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
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
public class DocumentsController implements pl.piotrowski.remotetexteditor.application.DocumentsController {

    private DocumentsService documentsService;

    @Autowired
    public DocumentsController(DocumentsService documentsService) {
        this.documentsService = documentsService;
    }

    @Override
    @MessageMapping("/update/{name}")
    @SendTo("/topic/updates/{name}")
    public Update updateDocumentsContent(@DestinationVariable String name, @Payload Update update) {
        Document document;



        try {
            document = documentsService.updateDocumentsContent(name, update);
        } catch (DocumentNotFoundException e) {
            e.printStackTrace();
        }


        return update;
    }

    @Override
    @GetMapping("/{name}")
    public ResponseEntity<Document> getDocument(@PathVariable String name) {
        try {
            return ResponseEntity.ok().body(documentsService.getDocument(name));
        } catch (DocumentNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(404).build();
        }
    }

    @Override
    @PostMapping
    public ResponseEntity<Document> createDocument(@RequestBody Document document) {
        try {
            document = documentsService.addDocument(document);
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
            documentsService.removeDocument(name);
        } catch (DocumentNotFoundException e) {
            return ResponseEntity.notFound().build();
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
    public ResponseEntity<String> renameDocument(@PathVariable String name, @RequestParam String newName) {
        try {
            String string = documentsService.changeDocumentsName(name, newName).getName();
            return ResponseEntity.ok(string);
        } catch (DocumentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }
}
