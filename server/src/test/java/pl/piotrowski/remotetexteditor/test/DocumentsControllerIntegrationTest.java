package pl.piotrowski.remotetexteditor.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.context.ContextConfiguration;
import pl.piotrowski.remotetexteditor.test.configuration.TestContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.piotrowski.remotetexteditor.Application;
import pl.piotrowski.remotetexteditor.application.DocumentsService;
import pl.piotrowski.remotetexteditor.controller.DocumentsController;
import pl.piotrowski.remotetexteditor.model.Document;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(DocumentsController.class)
@ContextConfiguration(classes = {TestContext.class, Application.class})
public class DocumentsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    Supplier<Document> testDocumentFactory;
    @Autowired
    Supplier<HashSet<Document>> testDocumentsSetFactory;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private DocumentsService documentsService;

    @Test
    public void getDocumentTest() throws Exception {

        Document document = testDocumentFactory.get();

        given(documentsService.getDocument(document.getName())).willReturn(document);

        mockMvc.perform(get("/docs/{name}", document.getName())).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(document.getName()))
                .andExpect(jsonPath("$.content").value(document.getContent()))
                .andExpect(jsonPath("$.id").value(document.getId())).andReturn();

    }

    @Test
    public void getAllDocumentsTest() throws Exception {

        HashSet<Document> documents = testDocumentsSetFactory.get();

        given(documentsService.getAllDocuments()).willReturn(documents);

        MvcResult mvcResult = mockMvc.perform(get("/docs")).andExpect(status().isOk()).andReturn();
        HashSet<Document> documentsFromResponse = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(),
                        new TypeReference<HashSet<Document>>() {
        });

        assertEquals(documents, documentsFromResponse);
    }

    @Test
    public void deleteDocumentTest() throws Exception {

        Document document = testDocumentFactory.get();

        willDoNothing().given(documentsService).removeDocument(document.getName());
        given(documentsService.getDocument(document.getName())).willReturn(document);

        MvcResult mvcResult = mockMvc.perform(delete("/docs/{name}/delete", document.getName()))
                .andExpect(status().isOk()).andReturn();

        HashMap<String,Document> response = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(),
                        new TypeReference<HashMap<String,Document>>(){});

        assertEquals(response.get("deleted").getName(), document.getName());


    }

    @Test
    public void createDocumentTest() throws Exception {
        Document document = testDocumentFactory.get();

        willDoNothing().given(documentsService).addDocument(document);

        MvcResult mvcResult = mockMvc.perform(post("/docs")
                .param("name", document.getName())
                .param("content", document.getContent()))
                .andExpect(status().isOk()).andReturn();

        Document addedDocument = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Document.class);

        assertEquals(document, addedDocument);
    }

    @Test
    public void renameTest() throws Exception {
        Document document = testDocumentFactory.get();
        String newName = "Foo";

        willDoNothing().given(documentsService).changeDocumentsName(document.getName(), newName);

        mockMvc.perform(patch("/docs/{name}", document.getName()).param("newName", newName))
                .andExpect(status().isOk()).andReturn();

    }


}
