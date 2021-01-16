package org.antop.billiardslove.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.config.JwtTokenProvider;
import org.antop.billiardslove.jpa.entity.KakaoLogin;
import org.antop.billiardslove.service.LoggedInService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LoggedInApi {

    private final LoggedInService loggedInService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/api/v1/logged-in")
    public LoggedInResponse loggedIn(@RequestBody LoggedInRequest request) {
        log.debug("{}", request);
        log.debug("LocalDateTime = {}", request.getConnectedAt());

        String token = jwtTokenProvider.createToken(request.getId());

        KakaoDto kakaoDto = KakaoDto.builder()
                .id(request.getId())
                .connectedAt(request.getConnectedAt())
                .build();
        // 사용자 동의 시 프로필 제공
        if (request.getProfile().isNeedsAgreement()) {
            kakaoDto.setNickname(request.getProfile().getNickname());
            kakaoDto.setImageUrl(request.getProfile().getImageUrl());
            kakaoDto.setThumbnailUrl(request.getProfile().getThumbnailUrl());
        }

        KakaoLogin kakaoLogin = loggedInService.getKakaoInfo(kakaoDto);
        MemberDto memberDto = loggedInService.getMemberInfo(kakaoLogin, kakaoDto);

        return LoggedInResponse.builder().token(token).member(memberDto).build();

    }

}
