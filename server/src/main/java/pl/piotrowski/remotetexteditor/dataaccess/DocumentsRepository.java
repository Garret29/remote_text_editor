package pl.piotrowski.remotetexteditor.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.piotrowski.remotetexteditor.model.Document;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface DocumentsRepository extends JpaRepository<Document, Long> {
    Optional<Document> getByName(String name);
    Stream<Document> getAll();
    Optional<Document> deleteByName(String name);
}
