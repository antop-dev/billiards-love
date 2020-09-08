package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
