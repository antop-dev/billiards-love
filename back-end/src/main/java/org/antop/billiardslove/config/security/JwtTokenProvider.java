package org.antop.billiardslove.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.config.properties.JwtProperties;
import org.antop.billiardslove.util.TemporalUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;

    /**
     * 토큰생성
     *
     * @param subject 토큰 제목
     * @return JWT 토큰
     */
    public String createToken(String subject) {
        Claims claims = Jwts.claims().setSubject(subject); // JWT payload 에 저장되는 정보단위

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = now.plus(jwtProperties.getDuration());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(TemporalUtils.toDate(now))
                .setExpiration(TemporalUtils.toDate(expiration))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }


    /**
     * 토큰에서 Subject 추출
     *
     * @param token 토큰
     * @return 회원 정보
     */
    public String getSubject(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token).getBody().getSubject();
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
