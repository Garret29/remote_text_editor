package pl.piotrowski.remotetexteditor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrowski.remotetexteditor.model.Document;
import pl.piotrowski.remotetexteditor.dataaccess.DocumentsRepository;
import pl.piotrowski.remotetexteditor.service.exceptions.DocumentAlreadyExistsException;
import pl.piotrowski.remotetexteditor.service.exceptions.DocumentNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DocumentsService implements pl.piotrowski.remotetexteditor.application.DocumentsService {

    private DocumentsRepository documentsRepository;

    @Autowired
    public DocumentsService(DocumentsRepository documentsRepository) {
        this.documentsRepository = documentsRepository;
    }

    @Override
    public Document addDocument(Document document) {
        return documentsRepository.save(document);
    }

    @Override
    public Document removeDocument(String name) throws DocumentNotFoundException {
         Optional<Document> optionalDocument = documentsRepository.deleteByName(name);
         return optionalDocument.orElseThrow(
                 ()-> new DocumentNotFoundException("Document with name '"+name+"' cannot be found!"));
    }

    @Override
    public Document changeDocumentsName(String oldName, String newName) throws DocumentNotFoundException {
        Optional<Document> optionalDocument = documentsRepository.getByName(oldName);
        Document found = optionalDocument.orElseThrow(
                ()-> new DocumentNotFoundException("Document with name '"+oldName+"' cannot be found!"));
        found.setName(newName);
        return documentsRepository.save(found);
    }

    @Override
    public Document updateDocumentsContent(String name, String newContent) throws DocumentNotFoundException {
        Optional<Document> optionalDocument = documentsRepository.getByName(name);
        Document found = optionalDocument.orElseThrow(
                ()-> new DocumentNotFoundException("Document with name '"+name+"' cannot be found!"));
        found.setContent(newContent);
        return documentsRepository.save(found);
    }

    @Override
    public Document getDocument(String name) throws DocumentNotFoundException {
        Optional<Document> optionalDocument = documentsRepository.getByName(name);

        return optionalDocument.orElseThrow(
                ()-> new DocumentNotFoundException("Document with name '"+name+"' cannot be found!"));
    }

    @Override
    public HashSet<Document> getAllDocuments() {
        return (HashSet<Document>)documentsRepository.getAll().collect(Collectors.toSet());
    }
}
