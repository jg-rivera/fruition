package dev.jgrivera.fruition.badge;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SmokeTest {
    @Autowired
    private BadgeController controller;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }
}
