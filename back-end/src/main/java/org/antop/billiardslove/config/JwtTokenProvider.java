package org.antop.billiardslove.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.config.properties.JwtProperties;
import org.antop.billiardslove.util.TemporalUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;

    /**
     * 토큰생성
     *
     * @param memberId 회원 아이디
     * @return JWT 토큰
     */
    public String createToken(long memberId) {
        Claims claims = Jwts.claims().setSubject("" + memberId); // JWT payload 에 저장되는 정보단위

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = now.plus(jwtProperties.getDuration());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(TemporalUtil.toDate(now))
                .setExpiration(TemporalUtil.toDate(expiration))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
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
