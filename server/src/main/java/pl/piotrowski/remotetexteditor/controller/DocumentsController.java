package pl.piotrowski.remotetexteditor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.piotrowski.remotetexteditor.service.DocumentsService;


@Controller
@RequestMapping("/docs")
public class DocumentsController implements pl.piotrowski.remotetexteditor.application.DocumentsController {

    private DocumentsService documentsService;

    @Autowired
    public DocumentsController(DocumentsService documentsService) {
        this.documentsService = documentsService;
    }

    @Override
    @MessageMapping("/{name}")
    @SendTo("/topic/updates")
    public ResponseEntity<?> updateDocument(@PathVariable String name, @RequestBody String content) {

        return null;
    }

    @Override
    @PostMapping("/{name}")
    public ResponseEntity<?> createDocument(@PathVariable String name) {
        return null;
    }

    @Override
    @PostMapping("/{name}/delete")
    public ResponseEntity<?> deleteDocument(@PathVariable String name) {
        return null;
    }

    @Override
    @GetMapping("/{name}")
    public ResponseEntity<?> getDocument(@PathVariable String name) {
        return null;
    }

    @Override
    @GetMapping
    public ResponseEntity<?> getDocuments() {
        return null;
    }
}
