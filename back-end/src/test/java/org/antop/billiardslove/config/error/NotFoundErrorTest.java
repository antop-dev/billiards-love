package org.antop.billiardslove.config.error;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.exception.MemberNotFountException;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(NotFoundErrorTest.TempController.class)
@WithMockUser(roles = "USER")
class NotFoundErrorTest extends SpringBootBase {
    private static final String URL = "/test/not-found";

    @Test
    void notFound() throws Exception {
        MockHttpServletRequestBuilder request = post(URL)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(HttpStatus.NOT_FOUND.value())));
    }

    @RestController
    static class TempController {
        @PostMapping(URL)
        public Number validate() {
            throw new MemberNotFountException();
        }

    }


}
