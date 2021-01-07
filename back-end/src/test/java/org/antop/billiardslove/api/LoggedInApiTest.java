package org.antop.billiardslove.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoggedInApi.class)
class LoggedInApiTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper om;

    @Test
    void loggedIn() throws Exception {
        Map<String, Object> request = ImmutableMap.of(
                "id", 11111L,
                "connectedAt", "2020-08-17T15:45:04Z",
                "profile", ImmutableMap.of(
                        "nickname", "nickname",
                        "imageUrl", "image url",
                        "thumbnailUrl", "thumbnail url",
                        "needsAgreement", true
                ));

        mockMvc
                .perform(post("/api/v1/logged-in")
                        .content(om.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }
}
