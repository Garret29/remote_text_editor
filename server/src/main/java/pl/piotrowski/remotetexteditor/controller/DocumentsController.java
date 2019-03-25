package pl.piotrowski.remotetexteditor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/docs")
public class DocumentsController implements pl.piotrowski.remotetexteditor.application.DocumentsController {
    @Override
    @MessageMapping("/{name}")
    @SendTo("/topic/updates")
    public ResponseEntity<?> updateDocument(@PathVariable String document, @RequestBody String content) {

        return null;
    }
}
