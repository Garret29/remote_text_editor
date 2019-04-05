package pl.piotrowski.remotetexteditor.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.piotrowski.remotetexteditor.model.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface DocumentsRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByName(String name);

    @Query("SELECT d FROM Document d")
    Stream<Document> getAll();

    @Modifying
    void deleteByName(String name);
}
