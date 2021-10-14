package org.antop.billiardslove.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.jpa.entity.Kakao;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.repository.MemberRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
@Transactional(readOnly = true)
public class MemberDao {
    private final MemberRepository repository;

    public Optional<Member> findById(long memberId) {
        return repository.findById(memberId);
    }

    /**
     * 카카오 프로필을 통해 멤버 조회
     *
     * @param kakao 카카오 프로필
     * @return 멤버 엔티티
     */
    public Optional<Member> findByKakao(Kakao kakao) {
        return repository.findByKakao(kakao);
    }

    @Transactional
    public Member save(Member member) {
        return repository.save(member);
    }
}
