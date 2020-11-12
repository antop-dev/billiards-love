package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.config.JwtTokenProvider;
import org.antop.billiardslove.hadler.EmptyException;
import org.antop.billiardslove.jpa.dto.LoginDto;
import org.antop.billiardslove.jpa.entity.Init;
import org.antop.billiardslove.jpa.entity.KakaoLogin;
import org.antop.billiardslove.jpa.repository.InitRepository;
import org.antop.billiardslove.jpa.repository.KakaoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InitService {

    private final InitRepository initRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final KakaoRepository kakaoRepository;

    public Init initAccount(String deviceId) {

        Optional<Init> init = initRepository.findById(deviceId);

        if (init.isPresent()) {
            return init.get();
        } else {
            return initRepository.save(
                    Init.builder()
                            .deviceId(deviceId)
                            .appId(UUID.randomUUID().toString().replaceAll("-", ""))
                            .build());
        }
    }

    public LoginDto login(String deviceId, String appId, KakaoLogin kakaoLogin) {
        LoginDto loginDto = new LoginDto();
        Init init = initRepository.findById(deviceId).orElseThrow(() -> new EmptyException(deviceId + "비어 있습니다."));

        if (init.getAppId().equals(deviceId) && init.getAppId().equals(appId)) {
            loginDto.setFirst(false);
        } else {
            kakaoRepository.save(kakaoLogin);
            loginDto.setFirst(true);
        }
        loginDto.setToken(jwtTokenProvider.createToken(deviceId, kakaoLogin));

        return loginDto;
    }

}
