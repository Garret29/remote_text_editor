package pl.piotrowski.remotetexteditor.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.piotrowski.remotetexteditor.Application;
import pl.piotrowski.remotetexteditor.application.DocumentsService;
import pl.piotrowski.remotetexteditor.configuration.TestContext;
import pl.piotrowski.remotetexteditor.model.Document;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DocumentsController.class)
@ContextConfiguration(classes = {TestContext.class, Application.class})
@ActiveProfiles("test")
class DocumentsControllerIntegrationTest {

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
    void getDocumentTest() throws Exception {

        Document document = testDocumentFactory.get();

        given(documentsService.getDocument(document.getName())).willReturn(document);

        mockMvc.perform(get("/docs/{name}", document.getName())).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(document.getName()))
                .andExpect(jsonPath("$.content").value(document.getContent()))
                .andExpect(jsonPath("$.id").value(document.getId())).andReturn();

    }

    @Test
    void getAllDocumentsTest() throws Exception {

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
    void deleteDocumentTest() throws Exception {

        Document document = testDocumentFactory.get();

        willDoNothing().given(documentsService).removeDocument(document.getName());
        given(documentsService.getDocument(document.getName())).willReturn(document);

        MvcResult mvcResult = mockMvc.perform(delete("/docs/{name}/delete", document.getName()))
                .andExpect(status().isOk()).andReturn();

        HashMap<String, Document> response = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(),
                        new TypeReference<HashMap<String, Document>>() {
                        });

        assertEquals(response.get("deleted").getName(), document.getName());


    }

    @Test
    void createDocumentTest() throws Exception {
        Document document = testDocumentFactory.get();

        given(documentsService.addDocument(document)).willReturn(document);

        MvcResult mvcResult = mockMvc.perform(post("/docs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(document)))
                .andExpect(status().isOk()).andReturn();

        Document addedDocument = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Document.class);

        assertEquals(document, addedDocument);
    }

    @Test
    void renameTest() throws Exception {
        Document document = testDocumentFactory.get();
        String newName = "Foo";

        given(documentsService.changeDocumentsName(document.getName(), newName)).willReturn(document);

        mockMvc.perform(patch("/docs/{name}", document.getName()).param("newName", newName))
                .andExpect(status().isOk()).andReturn();

    }


}
