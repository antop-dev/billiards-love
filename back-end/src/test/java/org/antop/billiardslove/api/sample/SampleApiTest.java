package org.antop.billiardslove.api.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.antop.billiardslove.context.WebConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({SampleApi.class, WebConfiguration.class})
class SampleApiTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper om;

    @Test
    void echo() throws Exception {
        mockMvc.perform(
                get("/sample/echo").param("s", "antop"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("antop"));
    }

    @Test
    void sum() throws Exception {
        Map<String, Integer> map = new HashMap<>();
        map.put("x", 10);
        map.put("y", 11);

        mockMvc.perform(
                post("/sample/sum")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(map)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(21)));
    }
}
