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

        String token = jwtTokenProvider.createToken(request.getId());

        KakaoDto kakaoDto = KakaoDto.builder()
                .id(request.getId())
                .nickname(request.getProfile().getNickname())
                .imageUrl(request.getProfile().getImageUrl())
                .thumbnailUrl(request.getProfile().getThumbnailUrl())
                .connectedAt(request.getConnectedAt())
                .build();

        MemberDto memberDto = loggedInService.registerMember(kakaoDto);

        org.antop.billiardslove.api.MemberDto memberDto1 = org.antop.billiardslove.api.MemberDto.builder()
                .id(memberDto.getId())
                .nickname(memberDto.getNickname())
                .handicap(memberDto.getHandicap())
                .thumbnail(memberDto.getThumbnail())
                .build();

        return LoggedInResponse.builder().token(token).member(memberDto1).build();

    }

}
