package kkashin.dev.lessons;

import kkashin.dev.lessons.model.Book;
import kkashin.dev.lessons.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createBook_shouldCreate() throws Exception {
        var book = new Book(
                null,
                "Book name",
                "Author",
                2024,
                100,
                6000
        );

        String bookJson = objectMapper.writeValueAsString(book);

        String createdBookJson = mockMvc.perform(
                post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        )
                .andExpect(status().is(201))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Book bookResponse = objectMapper.readValue(createdBookJson, Book.class);

        assertNotNull(bookResponse.id());
        assertEquals("Book name", bookResponse.name());
    }

    @Test
    void createBook_shouldFailWhenRequestNotValid() throws Exception {
        var book = new Book(
                null,
                null,
                "Author",
                2024,
                100,
                5000
        );

        String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson)
        )
                .andExpect(status().is(400));
    }

    @Test
    void findById_shouldReturnBook() throws Exception {
        var book = new Book(
                null,
                "name",
                "author",
                2024,
                100,
                5000
        );

        book = bookService.createBook(book);

        var response = mockMvc.perform(get("/books/{id}", book.id()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var foundBook = objectMapper.readValue(response, Book.class);

        assertThat(book).usingRecursiveAssertion().isEqualTo(foundBook);
    }

    @Test
    void findById_shouldThrowNotFound() throws Exception {
        mockMvc.perform(get("/books/{id}", 10L))
                .andExpect(status().is(404));
    }
}
