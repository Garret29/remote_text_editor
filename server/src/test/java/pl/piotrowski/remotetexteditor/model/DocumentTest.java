package pl.piotrowski.remotetexteditor.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DocumentTest {
    private Document document;

    @BeforeEach
    void setUp(){
        document = new Document("HW", "Hello World");
    }

    @Test
    void insertionTest(){
        document.insertContent("123",5);
        assertEquals("Hello123 World", document.getContent());
    }

    @Test
    void replacementTest(){
        document.replaceContent("311", 1);
        assertEquals("H311o World", document.getContent());
    }
}
