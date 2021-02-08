package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
