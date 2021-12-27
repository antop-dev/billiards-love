package org.antop.billiardslove.api;

import org.antop.billiardslove.WebMvcBase;
import org.antop.billiardslove.api.InitApi.Response;
import org.antop.billiardslove.config.properties.GoogleProperties;
import org.antop.billiardslove.config.properties.KakaoProperties;
import org.antop.billiardslove.util.Aes256Util;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.FieldDescriptor;
import util.JsonUtils;

import java.util.Arrays;
import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.antop.billiardslove.RestDocsUtils.CustomAttributes.encrypted;
import static org.hamcrest.IsBase64.isBase64;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InitApi.class)
class InitApiTest extends WebMvcBase {
    @MockBean
    private KakaoProperties kakaoProperties;
    @MockBean
    private GoogleProperties googleProperties;

    @Test
    void init() throws Exception {
        when(kakaoProperties.getJavaScriptKey()).thenReturn("kakao-java-script-key");
        when(googleProperties.getAdSenseKey()).thenReturn("google-adsense-key");

        String json = mockMvc.perform(post("/api/v1/init"))
                .andDo(print())
                .andDo(document("init", responseFields(initResponseFields())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.secretKey", isBase64()))
                .andExpect(jsonPath("$.kakaoKey", isBase64()))
                .andExpect(jsonPath("$.adSenseKey", isBase64()))
                .andReturn().getResponse().getContentAsString();

        Response response = JsonUtils.fromJson(json, Response.class);
        String secretKey = response.getSecretKey();
        assertThat(Aes256Util.decrypt(response.getKakaoKey(), secretKey), is(kakaoProperties.getJavaScriptKey()));
        assertThat(Aes256Util.decrypt(response.getAdSenseKey(), secretKey), is(googleProperties.getAdSenseKey()));
    }

    private List<FieldDescriptor> initResponseFields() {
        return Arrays.asList(
                fieldWithPath(InitApi.Response.Fields.SECRET_KEY).description("암복호화 키"),
                fieldWithPath(InitApi.Response.Fields.KAKAO_KEY).description("카카오 API 키").attributes(encrypted()),
                fieldWithPath(InitApi.Response.Fields.AD_SENSE_KEY).description("구글 애드센스 키").attributes(encrypted())
        );
    }

}
