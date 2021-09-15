package org.antop.billiardslove;

import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.dto.PlayerDto;
import org.antop.billiardslove.dto.MemberDto;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.headers.HeaderDescriptor;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.antop.billiardslove.RestDocsUtils.Attributes.encrypted;
import static org.antop.billiardslove.config.error.ErrorMessage.Fields.CODE;
import static org.antop.billiardslove.config.error.ErrorMessage.Fields.MESSAGE;
import static org.antop.billiardslove.dto.ContestDto.Fields.DESCRIPTION;
import static org.antop.billiardslove.dto.ContestDto.Fields.END_DATE;
import static org.antop.billiardslove.dto.ContestDto.Fields.END_TIME;
import static org.antop.billiardslove.dto.ContestDto.Fields.MAX_JOINER;
import static org.antop.billiardslove.dto.ContestDto.Fields.PLAYER;
import static org.antop.billiardslove.dto.ContestDto.Fields.START_DATE;
import static org.antop.billiardslove.dto.ContestDto.Fields.START_TIME;
import static org.antop.billiardslove.dto.ContestDto.Fields.STATE_CODE;
import static org.antop.billiardslove.dto.ContestDto.Fields.STATE_NAME;
import static org.antop.billiardslove.dto.ContestDto.Fields.TITLE;
import static org.antop.billiardslove.dto.PlayerDto.Fields.HANDICAP;
import static org.antop.billiardslove.dto.PlayerDto.Fields.NICKNAME;
import static org.antop.billiardslove.dto.PlayerDto.Fields.NUMBER;
import static org.antop.billiardslove.dto.PlayerDto.Fields.RANK;
import static org.antop.billiardslove.dto.PlayerDto.Fields.SCORE;
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
        private FieldsSnippet() {
        }

        /**
         * 대회 필드
         */
        public static List<FieldDescriptor> contest() {
            return Arrays.asList(
                    fieldWithPath(ContestDto.Fields.ID).description("대회 아이디").type(JsonFieldType.NUMBER),
                    fieldWithPath(TITLE).description("대회명").type(JsonFieldType.STRING),
                    fieldWithPath(DESCRIPTION).description("대회 설명").type(JsonFieldType.STRING).optional(),
                    fieldWithPath(START_DATE).description("시작 날짜").type(JsonFieldType.STRING),
                    fieldWithPath(START_TIME).description("시작 시간").type(JsonFieldType.STRING).optional(),
                    fieldWithPath(END_DATE).description("종료 날짜").type(JsonFieldType.STRING).optional(),
                    fieldWithPath(END_TIME).description("종료 시간").type(JsonFieldType.STRING).optional(),
                    fieldWithPath(STATE_CODE).description("상태 코드").type(JsonFieldType.STRING),
                    fieldWithPath(STATE_NAME).description("상태명").type(JsonFieldType.STRING),
                    fieldWithPath(MAX_JOINER).description("최대 참가자 수").type(JsonFieldType.NUMBER).optional()
            );
        }

        /**
         * 내 선수 정보가 포함된 대회 필드
         */
        public static List<FieldDescriptor> contestWithPlayer() {
            List<FieldDescriptor> fields = new ArrayList<>(contest());
            fields.addAll(Arrays.asList(
                    fieldWithPath(PLAYER).description("내 선수 정보").optional(),
                    fieldWithPath(PLAYER + "." + PlayerDto.Fields.ID).description("선수 아이디").type(JsonFieldType.NUMBER),
                    fieldWithPath(PLAYER + "." + NICKNAME).description("선수 별명").type(JsonFieldType.STRING),
                    fieldWithPath(PLAYER + "." + NUMBER).description("선수 번호 (참가 번호)").type(JsonFieldType.NUMBER).optional(),
                    fieldWithPath(PLAYER + "." + HANDICAP).description("참가 핸디캡 (회원의 핸디캡과 참가 핸디캡은 다를 수 있다.").type(JsonFieldType.NUMBER).optional(),
                    fieldWithPath(PLAYER + "." + RANK).description("순위").type(JsonFieldType.NUMBER).optional(),
                    fieldWithPath(PLAYER + "." + SCORE).description("점수").type(JsonFieldType.NUMBER).optional()
            ));
            return fields;
        }

        /**
         * 내 선수 정보가 포함된 대회 목록 필드
         */
        public static List<FieldDescriptor> contestsWithPlayer() {
            return contestWithPlayer().stream().map(it -> {
                FieldDescriptor field = fieldWithPath("[]." + it.getPath())
                        .type(it.getType())
                        .description(it.getDescription());
                if (it.isOptional()) field.optional();
                for (Map.Entry<String, Object> e : it.getAttributes().entrySet()) {
                    field.attributes(org.springframework.restdocs.snippet.Attributes.key(e.getKey()).value(e.getValue()));
                }
                return field;
            }).collect(Collectors.toList());
        }

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
