package org.antop.billiardslove.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.config.JwtTokenProvider;
import org.antop.billiardslove.jpa.entity.Member;
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

        Member member = loggedInService.join(request.getId(), request.getProfile());

        if (member.getHandicap() != null) {

            MemberDto memberDto = MemberDto.builder()
                    .id(member.getId())
                    .nickname(request.getProfile().getNickname())
                    .handicap(member.getHandicap())
                    .thumbnail(request.getProfile().getThumbnailUrl())
                    .build();

            return LoggedInResponse.builder().token(token).member(memberDto).build();
        }

        return LoggedInResponse.builder()
                .token(token)
                .member(MemberDto.builder()
                        .id(member.getId())
                        .nickname(request.getProfile().getNickname())
                        .thumbnail(request.getProfile().getThumbnailUrl())
                        .build())
                .build();
    }

}
