package org.antop.billiardslove.dao;

import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.jpa.entity.Kakao;
import org.antop.billiardslove.jpa.repository.KakaoRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Repository
@Transactional(readOnly = true)
public class KakaoDao extends QuerydslRepositorySupport {
    private final KakaoRepository repository;

    public KakaoDao(KakaoRepository repository) {
        super(Kakao.class);
        this.repository = repository;
    }

    /**
     * kakao 계정 조회
     *
     * @param kakaoId 카카오 아이디
     * @return 카카오 엔티티
     */
    public Optional<Kakao> findById(long kakaoId) {
        return repository.findById(kakaoId);
    }

    @Transactional
    public Kakao save(Kakao kakao) {
        return repository.save(kakao);
    }
}
