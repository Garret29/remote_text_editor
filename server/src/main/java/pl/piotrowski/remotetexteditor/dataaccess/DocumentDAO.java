package pl.piotrowski.remotetexteditor.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.piotrowski.remotetexteditor.DocumentApp;
import pl.piotrowski.remotetexteditor.model.Document;

@Repository
public interface DocumentDAO extends JpaRepository<Document, Long>, DocumentApp.DocumentDAO {
}
