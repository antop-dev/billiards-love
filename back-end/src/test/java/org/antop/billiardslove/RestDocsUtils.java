package org.antop.billiardslove;

import org.antop.billiardslove.dto.ContestDto;
import org.antop.billiardslove.dto.MatchDto;
import org.antop.billiardslove.dto.MemberDto;
import org.antop.billiardslove.dto.PlayerDto;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.headers.HeaderDescriptor;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.antop.billiardslove.RestDocsUtils.CustomAttributes.encrypted;
import static org.antop.billiardslove.RestDocsUtils.Descrption.NL;
import static org.antop.billiardslove.config.error.ErrorMessage.Fields.CODE;
import static org.antop.billiardslove.config.error.ErrorMessage.Fields.MESSAGE;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

public class RestDocsUtils {

    private RestDocsUtils() {
    }

    public static RestDocumentationResultHandler error(String identifier) {
        return document(identifier,
                responseFields(
                        fieldWithPath(CODE).description("에러 코드"),
                        fieldWithPath(MESSAGE).description("에러 메세지")
                ));
    }

    /**
     * 성명란에서 자주 쓰이는 것(?)
     *
     * @author antop
     */
    public static class Descrption {
        private Descrption() {
        }

        /**
         * 줄바꿈
         */
        public static final String NL = " +\n";
    }

    /**
     * Spring REST Docs 커스텀 필드 정의
     *
     * @author antop
     */
    public static class CustomFields {
        private CustomFields() {
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

    /**
     * 추가 속성 정의
     *
     * @author antop
     */
    public static class CustomAttributes {
        private CustomAttributes() {
        }

        public static org.springframework.restdocs.snippet.Attributes.Attribute encrypted() {
            return org.springframework.restdocs.snippet.Attributes.key(CustomFields.ENCRYPTED).value(Boolean.TRUE);
        }

        public static org.springframework.restdocs.snippet.Attributes.Attribute type(Object type) {
            return org.springframework.restdocs.snippet.Attributes.key(CustomFields.TYPE).value(type);
        }
    }

    /**
     * 입출력 필드 명세
     *
     * @author antop
     */
    public static class FieldSnippet {
        private FieldSnippet() {
        }

        /**
         * 대회 필드
         */
        public static List<FieldDescriptor> contest() {
            List<FieldDescriptor> fields = new ArrayList<>();
            fields.add(fieldWithPath(ContestDto.Fields.ID).description("대회 아이디").type(NUMBER));
            fields.add(fieldWithPath(ContestDto.Fields.TITLE).description("대회명").type(STRING));
            fields.add(fieldWithPath(ContestDto.Fields.DESCRIPTION).description("대회 설명").type(STRING).optional());
            fields.add(fieldWithPath(ContestDto.Fields.START_DATE).description("시작 날짜").type(STRING));
            fields.add(fieldWithPath(ContestDto.Fields.START_TIME).description("시작 시간").type(STRING).optional());
            fields.add(fieldWithPath(ContestDto.Fields.END_DATE).description("종료 날짜").type(STRING).optional());
            fields.add(fieldWithPath(ContestDto.Fields.END_TIME).description("종료 시간").type(STRING).optional());
            fields.add(fieldWithPath(ContestDto.Fields.STATE_CODE).description("상태 코드").type(STRING));
            fields.add(fieldWithPath(ContestDto.Fields.STATE_NAME).description("상태명").type(STRING));
            fields.add(fieldWithPath(ContestDto.Fields.MAX_JOINER).description("최대 참가자 수").type(NUMBER).optional());
            fields.add(fieldWithPath(ContestDto.Fields.CURRENT_JOINER).description("현재 참가자 수").type(NUMBER));
            fields.add(fieldWithPath(ContestDto.Fields.PROGRESS).description("진행률 (%)").type(NUMBER));
            return fields;
        }

        /**
         * 내 선수 정보가 포함된 대회 필드
         */
        public static List<FieldDescriptor> contestWithPlayer() {
            List<FieldDescriptor> fields = contest();
            fields.add(fieldWithPath(ContestDto.Fields.PLAYER).type(OBJECT).description("선수 정보").optional());
            fields.addAll(appendPrefix(player(), ContestDto.Fields.PLAYER));
            return fields;
        }

        /**
         * 내 선수 정보가 포함된 대회 목록 필드
         */
        public static List<FieldDescriptor> contestsWithPlayer() {
            return appendPrefix(contestWithPlayer(), "[]");
        }

        /**
         * 회원
         */
        public static List<FieldDescriptor> member() {
            return Arrays.asList(
                    fieldWithPath(MemberDto.Fields.ID).description("회원 아이디"),
                    fieldWithPath(MemberDto.Fields.NICKNAME).description("별명").attributes(encrypted()),
                    fieldWithPath(MemberDto.Fields.THUMBNAIL).description("썸네일 이미지 URL"),
                    fieldWithPath(MemberDto.Fields.HANDICAP).description("핸디캡")
            );
        }

        /**
         * 선수
         */
        public static List<FieldDescriptor> player() {
            return Arrays.asList(
                    fieldWithPath(PlayerDto.Fields.ID).description("선수 아이디").type(NUMBER),
                    fieldWithPath(PlayerDto.Fields.NICKNAME).description("별명").type(STRING),
                    fieldWithPath(PlayerDto.Fields.HANDICAP).description("선수 아이디").type(NUMBER),
                    fieldWithPath(PlayerDto.Fields.NUMBER).description("선수 번호").type(NUMBER).optional(),
                    fieldWithPath(PlayerDto.Fields.RANK).description("순위").type(NUMBER).optional(),
                    fieldWithPath(PlayerDto.Fields.SCORE).description("점수").type(NUMBER).optional(),
                    fieldWithPath(PlayerDto.Fields.VARIATION).description("순위 변동" + NL + "양수: 순위 올라감." + NL + "0: 변동 없음" + NL + "음수: 순위 내려감.").type(NUMBER),
                    fieldWithPath(PlayerDto.Fields.PROGRESS).description("선수 개인 진행률").type(NUMBER)
            );
        }

        /**
         * 선수 목록
         */
        public static List<FieldDescriptor> players() {
            return appendPrefix(player(), "[]");
        }

        /**
         * 경기
         */
        public static List<FieldDescriptor> match() {
            List<FieldDescriptor> fields = new ArrayList<>();
            fields.add(fieldWithPath(MatchDto.Fields.ID).description("경기 아이디").type(NUMBER));
            fields.add(fieldWithPath(MatchDto.Fields.RESULT).description("경기 결과").type(ARRAY));
            fields.add(fieldWithPath(MatchDto.Fields.CLOSED).description("확정 여부" + NL + "true : 수정 불가").type(BOOLEAN));
            fields.add(fieldWithPath(MatchDto.Fields.OPPONENT).description("선수 정보").type(OBJECT));
            fields.addAll(appendPrefix(player(), MatchDto.Fields.OPPONENT));
            return fields;
        }

        /**
         * 경기 목록
         */
        public static List<FieldDescriptor> matches() {
            return appendPrefix(match(), "[]");
        }

        /**
         * 필드들의 path 앞에 {@code prefix}를 달아서 반환한다.<br>
         * {@code prefix} + "." + {@link FieldDescriptor#getPath()}
         *
         * @param fields 기존 필드 설명들
         * @param prefix 앞에 붙일 단어 ("." 제외)
         */
        private static List<FieldDescriptor> appendPrefix(List<FieldDescriptor> fields, String prefix) {
            return fields.stream().map(it -> {
                // 패스, 타입, 설명
                FieldDescriptor field = fieldWithPath(prefix + "." + it.getPath())
                        .type(it.getType())
                        .description(it.getDescription());
                // 선택
                if (it.isOptional()) field.optional();
                // 추가 속성 옮기기
                for (Map.Entry<String, Object> e : it.getAttributes().entrySet()) {
                    field.attributes(org.springframework.restdocs.snippet.Attributes.key(e.getKey()).value(e.getValue()));
                }
                return field;
            }).collect(Collectors.toList());
        }

    }

    /**
     * URL 경로 변수 명세
     *
     * @author antop
     */
    public static class PathParameter {

        private PathParameter() {
        }

        /**
         * 대회 아이디
         */
        public static ParameterDescriptor contestId() {
            return parameterWithName("id").description("대회 아이디").attributes(CustomAttributes.type(NUMBER));
        }

        /**
         * 경기 아이디
         */
        public static ParameterDescriptor matchId() {
            return parameterWithName("id").description("경기 아이디").attributes(CustomAttributes.type(NUMBER));
        }


    }

    /**
     * 해더 명세
     */
    public static class Header {
        private Header() {
        }

        public static HeaderDescriptor jwtToken() {
            return HeaderDocumentation
                    .headerWithName(HttpHeaders.AUTHORIZATION)
                    .description("JWT 토큰")
                    .attributes(CustomAttributes.type(STRING));
        }
    }

}
