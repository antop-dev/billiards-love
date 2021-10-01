package org.antop.billiardslove;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.antop.billiardslove.config.filter.HttpLoggingFilter;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.antop.billiardslove.config.security.PrincipalProvider;
import org.antop.billiardslove.constants.Profiles;
import org.antop.billiardslove.model.ContestState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.CACHE_CONTROL;
import static org.springframework.http.HttpHeaders.CONTENT_LENGTH;
import static org.springframework.http.HttpHeaders.EXPIRES;
import static org.springframework.http.HttpHeaders.HOST;
import static org.springframework.http.HttpHeaders.PRAGMA;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.removeHeaders;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.removeMatchingHeaders;

/**
 * 웹 레이어 슬라이스 테스트용 슈퍼 클래스
 */
@WebMvcTest
@ActiveProfiles(Profiles.TEST)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureRestDocs(uriPort = 9000)
@Import({TestSecurityConfig.class, RestDocsConfig.class})
abstract public class WebMvcBase {
    public static final String MANAGER = "1";
    public static final String USER = "2";

    protected MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private PrincipalProvider principalProvider;

    /**
     * 일반 유저 토큰으로 세팅
     */
    protected final String userToken() {
        return USER;
    }

    /**
     * 관리자 토큰으로 세팅
     */
    protected final String managerToken() {
        return MANAGER;
    }

    /**
     * 오브젝트를 JSON 문자열로 변경
     *
     * @param o 변경할 오브젝트
     * @return 변경된 문자열
     */
    protected final String toJson(Object o) throws JsonProcessingException {
        return objectMapper.writeValueAsString(o);
    }

    /**
     * JSON 문자열을 오브젝트로 변경
     *
     * @param json      JSON 문자열
     * @param valueType 변경할 클래스 타입
     */
    protected final <T> T fromJson(String json, Class<T> valueType) throws JsonProcessingException {
        return objectMapper.readValue(json, valueType);
    }

    @BeforeEach
    void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                })
                .addFilters(new HttpLoggingFilter(objectMapper))
                .apply(MockMvcRestDocumentation
                        .documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(
                                prettyPrint(),
                                removeHeaders(CONTENT_LENGTH, HOST)
                        )
                        .withResponseDefaults(
                                prettyPrint(),
                                removeHeaders(PRAGMA, EXPIRES, CONTENT_LENGTH, CACHE_CONTROL),
                                removeMatchingHeaders("^X-(.*)$")
                        )
                )
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

        // "1" : 관리자 권한의 토큰
        // "2" : 일반 유저 권한의 토큰
        when(jwtTokenProvider.validateToken(anyString())).thenReturn(Boolean.TRUE);
        when(jwtTokenProvider.getSubject(anyString())).thenAnswer(invocation -> invocation.getArgument(0));

        when(principalProvider.getPrincipal(any(Object.class))).thenAnswer(invocation -> {
            String subject = invocation.getArgument(0, String.class);
            return new PrincipalProvider.Principal(Long.parseLong(subject), subject.equals(MANAGER));
        });
    }

    /**
     * https://github.com/DiUS/java-faker
     */
    protected final Faker faker = new Faker(Locale.KOREA);

    /**
     * 데이터베이스에 코드로 들어있는 대회 상태명을 반환한다.
     *
     * @param state 대회 상태 Enum
     * @return 대회 상태명
     */
    protected String stateName(ContestState state) {
        switch (state) {
            case PROCEEDING:
                return "진행중";
            case ACCEPTING:
                return "접수중";
            case PREPARING:
                return "준비중";
            case STOPPED:
                return "중지";
            default:
                return "종료";
        }
    }
}
