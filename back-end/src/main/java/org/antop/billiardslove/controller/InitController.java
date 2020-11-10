package org.antop.billiardslove.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class InitController {

    @ResponseBody
    @GetMapping("/v1/init")
    public HashMap<String, String> initAccount(@RequestHeader("X-DEVICE-ID") String deviceId, @RequestHeader("X-REQUEST-ID") String requestId) {

        HashMap<String, String> map = new HashMap<String, String>();
        String decryption = "";
        char[] chars = requestId.toCharArray();

        // 알파벳 문자의 뒤 숫자와 자리를 바꿈.
        for (int i = 0; i < requestId.length(); i++) {
            if (chars[i] >= 65 && chars[i] <= 90) {
                char temp = chars[i];
                chars[i] = chars[i + 1];
                chars[i] = temp;
            }
            decryption += chars[i];
        }

        StringBuilder sb = new StringBuilder(decryption.replaceAll("[A-Z]", ""));   // 알파벳 문자를 제거.
        decryption = sb.reverse().toString();   // 문자열 뒤집기
        decryption = decryption.substring(0,4) + "-" + decryption.substring(4,6) + "-" +decryption.substring(6,8)+ "T" + decryption.substring(8,10) + ":" +decryption.substring(10,12) +":" + decryption.substring(12,14);

        LocalDateTime targetDateTime = LocalDateTime.parse(decryption, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // targetDateTime < 현재시간 && 현재시간 < targetDateTime 경우에만 검증.
        if (deviceId != null && LocalDateTime.now().isAfter(targetDateTime) && (LocalDateTime.now().isBefore(targetDateTime.plusMinutes(1)))){

            // service 에서 appId, kakaaoKey, encodeKey 만들어서 받아오기
//            map.put("appId", UUID.randomUUID().toString().replaceAll("-", ""));
//            map.put("kakaoKey", UUID.randomUUID().toString().replaceAll("-", ""));
//            map.put("encodeKey", UUID.randomUUID().toString().replaceAll("-", ""));
        }

        return map;
    }
}
