package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.entity.Kakao;
import org.antop.billiardslove.jpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    /**
     * 카카오 정보로 회원 찾기
     *
     * @param kakao 카카오 정보
     * @return 회원
     */
    Optional<Member> findByKakao(Kakao kakao);
}
