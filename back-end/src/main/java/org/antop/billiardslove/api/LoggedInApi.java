package org.antop.billiardslove.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.antop.billiardslove.constants.Security;
import org.antop.billiardslove.dto.KakaoDto;
import org.antop.billiardslove.dto.MemberDto;
import org.antop.billiardslove.service.LoggedInService;
import org.antop.billiardslove.util.Aes256Util;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.ZonedDateTime;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LoggedInApi {

    private final LoggedInService loggedInService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/api/v1/logged-in")
    public Response loggedIn(@RequestBody final Request request, HttpSession session) {
        String secretKey = (String) session.getAttribute(Security.SECRET_KEY);

        KakaoDto kakaoDto = KakaoDto.builder()
                .id(request.getId())
                .nickname(Aes256Util.decrypt(request.getProfile().getNickname(), secretKey))
                .imageUrl(request.getProfile().getImageUrl())
                .thumbnailUrl(request.getProfile().getThumbnailUrl())
                .connectedAt(request.getConnectedAt())
                .build();

        MemberDto member = loggedInService.loggedIn(kakaoDto);
        String token = jwtTokenProvider.createToken(member.getId());

        return Response.builder()
                .token(token)
                .member(Response.Member.builder()
                        .id(member.getId())
                        .nickname(Aes256Util.encrypt(member.getNickname(), secretKey))
                        .handicap(member.getHandicap())
                        .thumbnail(member.getThumbnail())
                        .manager(member.isManager())
                        .build())
                .build();

    }

    @Getter
    @ToString
    @FieldNameConstants
    @Builder
    public static class Request {
        /**
         * 카카오톡 아이디
         */
        private final long id;
        /**
         * 서비스에 연결 완료된 시각 (UTC)<br>
         * <a href="https://tools.ietf.org/html/rfc3339">RFC3339 internet date/time format</a>
         */
        private final ZonedDateTime connectedAt;
        /**
         * 프로필
         */
        private final Request.Profile profile;

        @JsonCreator
        public Request(@JsonProperty long id, @JsonProperty ZonedDateTime connectedAt, @JsonProperty Profile profile) {
            this.id = id;
            this.connectedAt = connectedAt;
            this.profile = profile;
        }

        @Getter
        @ToString
        @FieldNameConstants
        @Builder
        public static class Profile {
            /**
             * 닉네임
             */
            private final String nickname;
            /**
             * 프로필 이미지 URL
             */
            private final String imageUrl;
            /**
             * 프로필 미리보기 이미지 URL
             */
            private final String thumbnailUrl;
            /**
             * 사용자 동의시 프로필 제공 가능 여부
             */
            private final boolean needsAgreement;

            @JsonCreator
            public Profile(@JsonProperty String nickname,
                           @JsonProperty String imageUrl,
                           @JsonProperty String thumbnailUrl,
                           @JsonProperty boolean needsAgreement) {
                this.nickname = nickname;
                this.imageUrl = imageUrl;
                this.thumbnailUrl = thumbnailUrl;
                this.needsAgreement = needsAgreement;
            }
        }
    }

    @Getter
    @FieldNameConstants
    public static class Response {
        /**
         * JWT 토큰
         */
        private final String token;
        /**
         * 회원
         */
        private final Response.Member member;
        /**
         * 추가정보 등록 여부
         */
        private boolean registered;

        @Builder
        public Response(String token, Response.Member member) {
            this.token = token;
            this.member = member;
            if (member.getHandicap() != null) {
                registered = true;
            }
        }

        @Getter
        @Builder
        @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
        @FieldNameConstants
        public static class Member {
            /**
             * 회원 아이디
             */
            private final Long id;
            /**
             * 회원 별명
             */
            private final String nickname;
            /**
             * 회원 핸디캡
             */
            private final Integer handicap;
            /**
             * 회원 이미지 (카카오톡 프로필 썸네일)
             */
            private final String thumbnail;
            /**
             * 관리자 여부
             */
            private final boolean manager;
        }
    }

}
