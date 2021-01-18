package org.antop.billiardslove.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.antop.billiardslove.config.properties.GoogleProperties;
import org.antop.billiardslove.config.properties.KakaoProperties;
import org.antop.billiardslove.dto.output.InitResponse;
import org.antop.billiardslove.util.Aes256;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.IsBase64.isBase64;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class InitApiTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private KakaoProperties kakaoProperties;
    @Autowired
    private GoogleProperties googleProperties;

    @Test
    void init() throws Exception {
        String jsonString = mockMvc.perform(post("/api/v1/init"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.secretKey", isBase64()))
                .andExpect(jsonPath("$.kakaoKey", isBase64()))
                .andExpect(jsonPath("$.adSenseKey", isBase64()))
                .andReturn().getResponse().getContentAsString();

        InitResponse response = om.readValue(jsonString, InitResponse.class);
        String secretKey = response.getSecretKey();
        assertThat(Aes256.decrypt(response.getKakaoKey(), secretKey), is(kakaoProperties.getJavaScriptKey()));
        assertThat(Aes256.decrypt(response.getAdSenseKey(), secretKey), is(googleProperties.getAdSenseKey()));
    }

}
