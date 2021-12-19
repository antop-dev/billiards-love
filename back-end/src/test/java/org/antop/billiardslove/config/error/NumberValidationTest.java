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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NumberValidationTest.TempController.class)
@Import(NumberValidationTest.TempController.class)
class NumberValidationTest extends WebMvcBase {
    private static final String URL = "/test/number";

    @Test
    void mustNotNull() throws Exception {
        Map<String, Object> body = Collections.singletonMap("value", null);

        MockHttpServletRequestBuilder request = post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(body));

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.message", is("숫자를 입력해주세요.")));
    }

    @Test
    void mustNumber() throws Exception {
        /*
         * 숫자를 입력해야 하는데 문자가 입력으로 들어왔을 때
         */
        String value = "not number";
        Map<String, Object> body = Collections.singletonMap("value", value);

        MockHttpServletRequestBuilder request = post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(body));

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.message", is("잘못된 포멧입니다.")));
    }

    @Test
    void mustGreaterThen10() throws Exception {
        int value = 1;
        Map<String, Object> body = Collections.singletonMap("value", value);

        MockHttpServletRequestBuilder request = post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(body));

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.message", is("숫자를 10 이상 입력해주세요.")));
    }

    @Test
    void mustLessThen100() throws Exception {
        int value = 9999;
        Map<String, Object> body = Collections.singletonMap("value", value);

        MockHttpServletRequestBuilder request = post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(body));

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.message", is("숫자를 100 이하로 입력해주세요.")));
    }

    @Test
    void ok() throws Exception {
        Integer value = 50;
        Map<String, Object> body = Collections.singletonMap("value", value);

        MockHttpServletRequestBuilder request = post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(body));

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("" + value));
    }

    @RestController
    static class TempController {

        @PostMapping(URL)
        public Number validate(@RequestBody @Valid Request request) {
            return request.getValue();
        }

        static class Request {
            @NotNull(message = "숫자를 입력해주세요.")
            @Min(value = 10, message = "숫자를 10 이상 입력해주세요.")
            @Max(value = 100, message = "숫자를 100 이하로 입력해주세요.")
            private Number value;

            public Number getValue() {
                return value;
            }
        }
    }


}
