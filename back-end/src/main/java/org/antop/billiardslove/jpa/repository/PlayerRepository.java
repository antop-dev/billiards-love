package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByContestOrderByRankAsc(Contest contest);
}
