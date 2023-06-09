package dev.jgrivera.fruition.badge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class MockWebApplicationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BadgeRepository repository;

    @BeforeEach
    public void setup() {
        repository.deleteAll();
    }

    @Test
    public void it_gets_badges() throws Exception {
        Badge badge1 = repository.save(new Badge("Example badge 1"));
        Badge badge2 = repository.save(new Badge("Example badge 2"));

        mockMvc.perform(get("/api/badges"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(String.valueOf(badge1.getId()))))
                .andExpect(jsonPath("$[0].name", is(badge1.getName())))
                .andExpect(jsonPath("$[1].id", is(String.valueOf(badge2.getId()))))
                .andExpect(jsonPath("$[1].name", is(badge2.getName())));

        mockMvc.perform(get("/api/badges/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(String.valueOf(badge1.getId()))))
                .andExpect(jsonPath("$[0].name", is(badge1.getName())))
                .andExpect(jsonPath("$[1].id", is(String.valueOf(badge2.getId()))))
                .andExpect(jsonPath("$[1].name", is(badge2.getName())));
    }

    @Test
    public void it_gets_badge() throws Exception {
        Badge badge = repository.save(new Badge("Example badge"));
        String id = String.valueOf(badge.getId());
        String name = badge.getName();

        mockMvc.perform(get("/api/badges/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.name", is(name)));
    }

    @Test
    public void it_creates_badge() throws Exception {
        String body = "{\"name\": \"Example badge\"}";

        mockMvc.perform(post("/api/badges").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Successfully created badge")));

        mockMvc.perform(post("/api/badges/").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Successfully created badge")));
    }

    @Test
    public void it_validates_name_when_creating_badge() throws Exception {
        String body = "{\"name\": \"\"}";

        mockMvc.perform(post("/api/badges").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/api/badges/").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void it_updates_badge() throws Exception {
        Badge badge = repository.save(new Badge("Example badge"));
        String id = String.valueOf(badge.getId());
        String body = "{\"name\": \"Updated badge\"}";

        mockMvc.perform(put("/api/badges/{id}", id).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated badge")));
    }

    @Test
    public void it_validates_name_when_updating_badge() throws Exception {
        Badge badge = repository.save(new Badge("Example badge"));
        String id = String.valueOf(badge.getId());
        String body = "{\"name\": \"\"}";

        mockMvc.perform(put("/api/badges/{id}", id).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void it_does_not_update_non_existent_badge() throws Exception {
        String id = String.valueOf(UUID.randomUUID());
        String body = "{\"name\": \"Updated badge\"}";

        mockMvc.perform(put("/api/badges/{id}", id).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void it_deletes_badge() throws Exception {
        Badge badge = repository.save(new Badge("Example badge"));
        String id = String.valueOf(badge.getId());

        mockMvc.perform(delete("/api/badges/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void it_does_not_delete_non_existent_badge() throws Exception {
        String id = String.valueOf(UUID.randomUUID());

        mockMvc.perform(delete("/api/badges/{id}", id))
                .andExpect(status().isNotFound());
    }
}
