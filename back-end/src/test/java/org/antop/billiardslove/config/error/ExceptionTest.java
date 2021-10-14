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

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ExceptionTest.TempController.class)
@Import(ExceptionTest.TempController.class)
class ExceptionTest extends WebMvcBase {
    private static final String URL = "/test/not-blank";

    @Test
    void exception() throws Exception {
        /*
         * 0으로 나눌 수 없는 예외가 발생하게 된다.
         */
        MockHttpServletRequestBuilder request = post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("0");

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("$.code", is(HttpStatus.INTERNAL_SERVER_ERROR.value())))
                .andExpect(jsonPath("$.message", is("/ by zero")));
    }

    @RestController
    static class TempController {
        @PostMapping(URL)
        public String validate(@RequestBody int n) {
            return "" + (100 / n);
        }
    }

}
