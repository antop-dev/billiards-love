package org.antop.billiardslove.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.config.JwtTokenProvider;
import org.antop.billiardslove.dto.KakaoDto;
import org.antop.billiardslove.dto.MemberDto;
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
        KakaoDto kakaoDto = KakaoDto.builder()
                .id(request.getId())
                .nickname(request.getProfile().getNickname())
                .imageUrl(request.getProfile().getImageUrl())
                .thumbnailUrl(request.getProfile().getThumbnailUrl())
                .connectedAt(request.getConnectedAt())
                .build();

        MemberDto member = loggedInService.loggedIn(kakaoDto);

        String token = jwtTokenProvider.createToken(member.getId());

        return LoggedInResponse.builder()
                .token(token)
                .member(LoggedInResponse.Member.builder()
                        .id(member.getId())
                        .nickname(member.getNickname())
                        .handicap(member.getHandicap())
                        .thumbnail(member.getThumbnail())
                        .build())
                .build();

    }

}
