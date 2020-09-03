package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.entity.GameResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameResultRepository extends JpaRepository<GameResult, Long> {
}
