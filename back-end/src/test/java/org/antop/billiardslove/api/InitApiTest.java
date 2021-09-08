package org.antop.billiardslove.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.api.InitApi.Response;
import org.antop.billiardslove.config.properties.GoogleProperties;
import org.antop.billiardslove.config.properties.KakaoProperties;
import org.antop.billiardslove.util.Aes256Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.antop.billiardslove.RestDocsUtils.Attributes.encrypted;
import static org.hamcrest.IsBase64.isBase64;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class InitApiTest extends SpringBootBase {
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
                .andDo(document("init",
                        responseFields(
                                fieldWithPath(Response.Fields.SECRET_KEY).description("암복호화 키"),
                                fieldWithPath(Response.Fields.KAKAO_KEY)
                                        .description("카카오 API 키")
                                        .attributes(encrypted()),
                                fieldWithPath(Response.Fields.AD_SENSE_KEY)
                                        .description("구글 애드센스 키")
                                        .attributes(encrypted())
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.secretKey", isBase64()))
                .andExpect(jsonPath("$.kakaoKey", isBase64()))
                .andExpect(jsonPath("$.adSenseKey", isBase64()))
                .andReturn().getResponse().getContentAsString();

        Response response = om.readValue(jsonString, Response.class);
        String secretKey = response.getSecretKey();
        assertThat(Aes256Util.decrypt(response.getKakaoKey(), secretKey), is(kakaoProperties.getJavaScriptKey()));
        assertThat(Aes256Util.decrypt(response.getAdSenseKey(), secretKey), is(googleProperties.getAdSenseKey()));
    }

}
