package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.entity.KakaoLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KakaoRepository extends JpaRepository<KakaoLogin, Long> {
}
