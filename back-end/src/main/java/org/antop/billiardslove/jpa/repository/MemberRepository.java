package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.entity.KakaoLogin;
import org.antop.billiardslove.jpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByKakaoLogin(KakaoLogin kakaoLogin);
}
