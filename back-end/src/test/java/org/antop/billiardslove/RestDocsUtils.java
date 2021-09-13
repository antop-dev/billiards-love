package org.antop.billiardslove;

import org.antop.billiardslove.dto.MemberDto;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.headers.HeaderDescriptor;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.Arrays;
import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.antop.billiardslove.RestDocsUtils.Attributes.encrypted;
import static org.antop.billiardslove.config.error.ErrorMessage.Fields.CODE;
import static org.antop.billiardslove.config.error.ErrorMessage.Fields.MESSAGE;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

public class RestDocsUtils {

    private RestDocsUtils() {
    }

    public static HeaderDescriptor jwtToken() {
        return HeaderDocumentation
                .headerWithName(HttpHeaders.AUTHORIZATION)
                .description("JWT 토큰")
                .attributes(Attributes.type(JsonFieldType.STRING));
    }

    public static RestDocumentationResultHandler error(String identifier) {
        return document(identifier,
                responseFields(
                        fieldWithPath(CODE).description("에러 코드"),
                        fieldWithPath(MESSAGE).description("에러 메세지")
                ));
    }

    /**
     * Spring REST Docs 커스텀 필드 정의
     *
     * @author antop
     */
    public static class Fields {
        private Fields() {
        }

        /**
         * 타입<br>
         * path variable, header 에는 기본으로 없다.
         */
        public static final String TYPE = "type";
        /**
         * 암호화 여부
         */
        public static final String ENCRYPTED = "encrypted";
    }

    public static class Attributes {
        private Attributes() {
        }

        public static org.springframework.restdocs.snippet.Attributes.Attribute encrypted() {
            return org.springframework.restdocs.snippet.Attributes.key(Fields.ENCRYPTED).value(Boolean.TRUE);
        }

        public static org.springframework.restdocs.snippet.Attributes.Attribute type(Object type) {
            return org.springframework.restdocs.snippet.Attributes.key(Fields.TYPE).value(type);
        }
    }

    public static class FieldsSnippet {
        private FieldsSnippet() {}

        public static List<FieldDescriptor> member() {
            return Arrays.asList(
                    fieldWithPath(MemberDto.Fields.ID).description("회원 아이디"),
                    fieldWithPath(MemberDto.Fields.NICKNAME).description("별명").attributes(encrypted()),
                    fieldWithPath(MemberDto.Fields.THUMBNAIL).description("썸네일 이미지 URL"),
                    fieldWithPath(MemberDto.Fields.HANDICAP).description("핸디캡")
            );
        }

    }

}
