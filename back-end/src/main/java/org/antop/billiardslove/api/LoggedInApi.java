package org.antop.billiardslove.api;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.config.security.JwtTokenProvider;
import org.antop.billiardslove.dto.KakaoDto;
import org.antop.billiardslove.dto.MemberDto;
import org.antop.billiardslove.service.LoggedInService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LoggedInApi {

    private final LoggedInService loggedInService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/api/v1/logged-in")
    public Response loggedIn(@RequestBody final Request request) {
        KakaoDto kakaoDto = KakaoDto.builder()
                .id(request.getId())
                .nickname(request.getProfile().getNickname())
                .imageUrl(request.getProfile().getImageUrl())
                .thumbnailUrl(request.getProfile().getThumbnailUrl())
                .connectedAt(request.getConnectedAt())
                .build();

        MemberDto member = loggedInService.loggedIn(kakaoDto);

        String token = jwtTokenProvider.createToken("" + member.getId());

        return Response.builder()
                .token(token)
                .member(Response.Member.builder()
                        .id(member.getId())
                        .nickname(member.getNickname())
                        .handicap(member.getHandicap())
                        .thumbnail(member.getThumbnail())
                        .build())
                .build();

    }

    @Getter
    @NoArgsConstructor
    @ToString
    public static class Request {
        /**
         * 카카오톡 아이디
         */
        private long id;
        /**
         * 서비스에 연결 완료된 시각 (UTC)
         */
        private ZonedDateTime connectedAt;
        /**
         * 프로필
         */
        private Request.Profile profile;

        /**
         * {@link ZonedDateTime}을 시스템 타임존으로 변경된 {@link LocalDateTime}으로 변환해서 준다.
         *
         * @return {@link LocalDateTime}
         */
        public LocalDateTime getConnectedAt() {
            return connectedAt.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
        }

        @Getter
        @NoArgsConstructor
        @ToString
        public static class Profile {
            /**
             * 닉네임
             */
            private String nickname;
            /**
             * 프로필 이미지 URL
             */
            private String imageUrl;
            /**
             * 프로필 미리보기 이미지 URL
             */
            private String thumbnailUrl;
            /**
             * 사용자 동의시 프로필 제공 가능 여부
             */
            private boolean needsAgreement;
        }
    }

    @Getter
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
        static class Member {
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
        }
    }

}
