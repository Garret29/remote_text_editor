package pl.piotrowski.remotetexteditor.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.piotrowski.remotetexteditor.Application;
import pl.piotrowski.remotetexteditor.configuration.TestContext;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestContext.class, Application.class})
@ActiveProfiles("test")
class DocumentTest {
    private Document document;
    @Autowired
    Supplier<Document> testDocumentFactory;
    @Autowired
    Supplier<Update> testUpdateFactory;


    @BeforeEach
    void setUp(){
        document = testDocumentFactory.get();
    }

    @Test
    void insertionTest(){
        int length = document.getContent().length();
        String testString = "123";
        document.insertContent(testString,5);

        assertEquals(length+ testString.length(), document.getContent().length());
        assertEquals(testString, document.getContent().substring(5,8));
    }

    @Test
    void replacementTest(){
        document.replaceContent("311", 1);
        assertEquals("H311o World", document.getContent());
    }

    @Test
    void applyUpdate(){
        Update update = testUpdateFactory.get();
        document.applyUpdate(update);

        assertEquals(update.getContent(), document.getContent().substring(update.getStart(), update.getStart()+update.getContent().length()));
    }
}
