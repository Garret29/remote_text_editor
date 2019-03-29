package pl.piotrowski.remotetexteditor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrowski.remotetexteditor.model.Document;
import pl.piotrowski.remotetexteditor.dataaccess.DocumentsDAO;

import java.util.HashSet;

@Service
public class DocumentsService implements pl.piotrowski.remotetexteditor.application.DocumentsService {

    private DocumentsDAO documentsDAO;

    @Autowired
    public DocumentsService(DocumentsDAO documentsDAO) {
        this.documentsDAO = documentsDAO;
    }

    @Override
    public void addDocument(Document document) {
        documentsDAO.saveAndFlush(document);
    }

    @Override
    public void removeDocument(String name) {

    }

    @Override
    public void changeDocumentsName(String oldName, String newName) {

    }

    @Override
    public void updateDocument(String name, String newContent) {

    }

    @Override
    public Document getDocument(String name) {
        return null;
    }

    @Override
    public HashSet<Document> getAllDocuments() {
        return null;
    }
}
