package org.antop.billiardslove.controller;

import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.hadler.EmptyException;
import org.antop.billiardslove.hadler.TimeLimitException;
import org.antop.billiardslove.jpa.dto.LoginDto;
import org.antop.billiardslove.jpa.entity.Init;
import org.antop.billiardslove.jpa.dto.InitResponse;
import org.antop.billiardslove.jpa.entity.KakaoLogin;
import org.antop.billiardslove.service.InitService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class InitController {

    public static final String DATE_TIME_FORMAT = "yyyyMMddHHmmss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    private final InitService initService;

    @ResponseBody
    @GetMapping("/init")
    public InitResponse initAccount(@RequestHeader("X-DEVICE-ID") String deviceId, @RequestHeader("X-REQUEST-ID") String requestId) {

        if (StringUtils.isAnyBlank(deviceId, requestId)) {
            throw new EmptyException("Header is Null.");
        }

        LocalDateTime dateTime = decryption(requestId);

        if (Duration.between(dateTime, LocalDateTime.now()).toMinutes() > 1) {
            throw new TimeLimitException();
        }

        Init init = initService.initAccount(deviceId);

        return InitResponse.builder()
                .appId(init.getAppId())
                .encodeKey(generateKey())
                .kakaoKey(generateKey())
                .build();
    }

    @ResponseBody
    @PostMapping("/logged-in")
    public LoginDto loginAccount(@RequestHeader("X-DEVICE-ID") String deviceId, @RequestHeader("X-APP-ID") String appId, @RequestBody KakaoLogin kakaoLogin) {

        if (StringUtils.isAnyBlank(deviceId, appId)) {
            throw new EmptyException("Header is Null.");
        }
        return initService.login(deviceId, appId, kakaoLogin);
    }

    /**
     * UUID 기반으로 랜덤 키값을 생성한다.
     *
     * @return UUID에서 "-" 문자가 제거된 32자리 랜덤 문자열
     */
    private String generateKey() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * X REQ ID 복호화
     *@param requestId "X-REQUEST-ID"
     * @return 복호화된 시간
     */
    private LocalDateTime decryption(String requestId){
        StringBuilder s = new StringBuilder(requestId.replaceAll("[A-Z]", ""));
        LocalDateTime dateTime = LocalDateTime.parse(s.reverse().toString(), DATE_TIME_FORMATTER);

        return dateTime;
    }
}
