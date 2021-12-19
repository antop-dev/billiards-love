package org.antop.billiardslove.config.error;

import org.antop.billiardslove.WebMvcBase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import util.JsonUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotBlankValidationTest.TempController.class)
@Import(NotBlankValidationTest.TempController.class)
class NotBlankValidationTest extends WebMvcBase {
    private static final String URL = "/test/not-blank";

    @Test
    void mustNotNull() throws Exception {
        Map<String, Object> body = Collections.singletonMap("value", null);

        MockHttpServletRequestBuilder request = post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(body));

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.message", is("값을 입력해주세요.")));
    }

    @Test
    void mustNotBlank() throws Exception {
        // 공백도 허용하지 않는다.
        Map<String, Object> body = Collections.singletonMap("value", "  ");

        MockHttpServletRequestBuilder request = post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(body));

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.message", is("값을 입력해주세요.")));
    }

    @Test
    void ok() throws Exception {
        String value = "not blank";
        Map<String, Object> body = Collections.singletonMap("value", value);

        MockHttpServletRequestBuilder request = post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(body));

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
            @NotBlank(message = "값을 입력해주세요.")
            private String value;

            public String getValue() {
                return value;
            }
        }
    }

}
