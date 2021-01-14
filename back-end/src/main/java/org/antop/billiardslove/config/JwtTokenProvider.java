package org.antop.billiardslove.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.config.properties.JwtProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;

    // 토큰 유효시간 30분
    private static final long tokenValidTime = 30 * 60 * 1000L;

    /**
     * 토큰생성
     *
     * @param deviceId   장비 아이디
     * @return JWT 토큰
     */
    public String createToken(Long deviceId) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(deviceId)); // JWT payload 에 저장되는 정보단위
        //claims.put("kakaoLogin", kakaoLogin); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }

    /**
     * 토큰에서 회원 정보 추출
     *
     * @param token 토큰
     * @return 회원 정보
     */
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Header에서 AUTHORIZATION 추출
     *
     * @param request
     * @return AUTHORIZATION
     */
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }

    /**
     * 토큰의 유효성 + 만료일자 확인
     *
     * @param jwtToken 토큰
     * @return 참일시 유효
     */
    public boolean validateToken(String jwtToken) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(jwtToken);
        return !claims.getBody().getExpiration().before(new Date());
    }
}
