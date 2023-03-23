package dev.jgrivera.fruition.badge;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class MockWebApplicationTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void should_get_badge() throws Exception {
        mvc.perform(get("/badge"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is("Test badge!")));
    }
}
