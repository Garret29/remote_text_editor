package pl.piotrowski.remotetexteditor.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.piotrowski.remotetexteditor.model.Document;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Document.class})
public class DocumentTest {
    private Document document;

    @Before
    public void setUp(){
        document = new Document("HW", "Hello World");
    }

    @Test
    public void insertionTest(){
        document.insertContent("123",5);
        assertEquals("Hello123 World", document.getContent());
    }

    @Test
    public void replacementTest(){
        document.replaceContent("311", 1);
        assertEquals("H311o World", document.getContent());
    }
}
