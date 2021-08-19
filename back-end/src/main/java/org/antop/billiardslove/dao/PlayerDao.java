package org.antop.billiardslove.dao;

import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.jpa.entity.Player;
import org.antop.billiardslove.jpa.repository.PlayerRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.antop.billiardslove.jpa.entity.QContest.contest;
import static org.antop.billiardslove.jpa.entity.QMember.member;
import static org.antop.billiardslove.jpa.entity.QPlayer.player;

@Slf4j
@Repository
@Transactional(readOnly = true)
public class PlayerDao extends QuerydslRepositorySupport {
    private final PlayerRepository repository;

    public PlayerDao(PlayerRepository repository) {
        super(Player.class);
        this.repository = repository;
    }

    /**
     * 해당 대회의 선수 정보를 조회한다.
     *
     * @param contestId 대회 아이디
     * @param memberId  회원 아이디
     * @return 선수 정보
     */
    public Optional<Player> findByContestAndMember(long contestId, long memberId) {
        return Optional.ofNullable(from(player).join(player.contest, contest)
                .fetchJoin()
                .join(player.member, member)
                .fetchJoin()
                .where(contest.id.eq(contestId).and(member.id.eq(memberId)))
                .fetchOne());
    }

    /**
     * 대회에 속한 선수 목록 조회
     *
     * @param contestId 대회 아이디
     */
    public List<Player> findByContest(long contestId) {
        return from(player).where(player.contest.id.eq(contestId)).fetch();
    }

    @Transactional
    public void remove(long playerId) {
        repository.deleteById(playerId);
    }

    @Transactional
    public void save(Player player) {
        repository.save(player);
    }

    public Optional<Player> findById(long playerId) {
        return repository.findById(playerId);
    }

}
