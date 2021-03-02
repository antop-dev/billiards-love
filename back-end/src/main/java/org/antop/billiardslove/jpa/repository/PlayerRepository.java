package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByContestAndMember(Contest contest, Member member);

}
