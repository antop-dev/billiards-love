package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.config.JwtTokenProvider;
import org.antop.billiardslove.hadler.EmptyException;
import org.antop.billiardslove.jpa.dto.LoginDto;
import org.antop.billiardslove.jpa.entity.Init;
import org.antop.billiardslove.jpa.entity.KakaoLogin;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.repository.InitRepository;
import org.antop.billiardslove.jpa.repository.KakaoRepository;
import org.antop.billiardslove.jpa.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InitService {

    private final InitRepository initRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final KakaoRepository kakaoRepository;
    private final MemberRepository memberRepository;

    public Init initAccount(String deviceId) {
        Optional<Init> init = Optional.of(initRepository.findById(deviceId).orElseGet(() -> initRepository.save(
                Init.builder()
                        .deviceId(deviceId)
                        .appId(UUID.randomUUID().toString().replaceAll("-", ""))
                        .build())));

        return init.get();
    }

    public LoginDto login(String deviceId, String appId, KakaoLogin kakaoLogin) {
        LoginDto loginDto = new LoginDto();
        Init init = initRepository.findById(deviceId).orElseThrow(() -> new EmptyException(deviceId + "비어 있습니다."));

        if (!init.getAppId().equals(deviceId) && !init.getAppId().equals(appId)) {
            kakaoRepository.save(kakaoLogin);
            memberRepository.save(Member.builder()
                    .nickname(kakaoLogin.getProfile().getNickname())
                    .build());
        }
        loginDto.setToken(jwtTokenProvider.createToken(deviceId, kakaoLogin));

        return loginDto;
    }

}
