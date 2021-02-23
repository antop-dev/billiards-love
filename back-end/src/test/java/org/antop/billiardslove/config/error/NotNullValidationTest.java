package org.antop.billiardslove.config.error;

import org.antop.billiardslove.SpringBootBase;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(NotNullValidationTest.TempController.class)
@WithMockUser(roles = "USER")
class NotNullValidationTest extends SpringBootBase {
    private static final String URL = "/test/not-null";

    @Test
    void mustNotNull() throws Exception {
        Map<String, Object> body = Collections.singletonMap("value", null);

        MockHttpServletRequestBuilder request = post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body));

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.message", is("value is must not be null (input : null)")));
    }

    @Test
    void ok() throws Exception {
        String value = "not null";
        Map<String, Object> body = Collections.singletonMap("value", value);

        MockHttpServletRequestBuilder request = post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body));

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(value));
    }

    @RestController
    static class TempController {
        @PostMapping(URL)
        public String validate(@RequestBody @Valid Request request) {
            return request.getValue();
        }

        static class Request {
            @NotNull
            private String value;

            public String getValue() {
                return value;
            }
        }
    }

}
