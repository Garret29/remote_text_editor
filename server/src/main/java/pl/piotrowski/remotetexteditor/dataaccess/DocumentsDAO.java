package pl.piotrowski.remotetexteditor.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.piotrowski.remotetexteditor.model.Document;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface DocumentsDAO extends JpaRepository<Document, String>, pl.piotrowski.remotetexteditor.application.DocumentsDAO {
    Optional<Document> getByName(String name);
}
