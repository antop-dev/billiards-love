package org.antop.billiardslove.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.dao.ContestDao;
import org.antop.billiardslove.dao.MatchDao;
import org.antop.billiardslove.dao.MemberDao;
import org.antop.billiardslove.dao.PlayerDao;
import org.antop.billiardslove.dto.PlayerDto;
import org.antop.billiardslove.exception.AlreadyJoinException;
import org.antop.billiardslove.exception.ContestNotFoundException;
import org.antop.billiardslove.exception.MemberNotFountException;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Match;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.entity.Player;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PlayerService {
    private final PlayerDao playerDao;
    private final ContestDao contestDao;
    private final MemberDao memberDao;
    private final MatchDao matchDao;

    /**
     * 선수 정보를 조회한다.
     *
     * @param contestId 대회 아이디
     * @param memberId  회원 아이디
     */
    public Optional<PlayerDto> getPlayer(long contestId, long memberId) {
        return playerDao.findByContestAndMember(contestId, memberId).map(this::convert);
    }

    /**
     * 대회에 참가한 선수 목록을 조회한다.
     *
     * @param contestId 대회 아이디
     */
    public List<PlayerDto> getPlayers(long contestId) {
        return playerDao.findByContest(contestId)
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    /**
     * 선수를 제거한다.
     *
     * @param playerId 선수 아이디
     */
    @Transactional
    public void remove(long playerId) {
        playerDao.remove(playerId);
    }

    /**
     * 대회에 속한 선수들을 초기화 한다.
     *
     * @param contestId 대회 아이디
     */
    @Transactional
    public void initPlayers(long contestId) {
        Contest contest = contestDao.findById(contestId).orElseThrow(ContestNotFoundException::new);

        List<Player> players = playerDao.findByContest(contestId);
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            p.setScore(0);
            p.setNumber(i + 1);
            p.setRank(i + 1);

            // 대진표 생성
            for (int j = i + 1; j < players.size(); j++) {
                Player opponent = players.get(j);

                Match match = Match.builder()
                        .contest(contest)
                        .player1(p)
                        .player2(opponent)
                        .build();

                matchDao.save(match);
            }
        }
    }

    /**
     * 대회에 맴버를 조인 시킨다.
     *
     * @param contestId 대회 아이디
     * @param memberId  회원 아이디
     */
    @Transactional
    public Player join(long contestId, long memberId, int handicap) {
        // 이미 참가한 회원인지 확인
        playerDao.findByContestAndMember(contestId, memberId).ifPresent(player -> {
            throw new AlreadyJoinException();
        });

        Contest contest = contestDao.findById(contestId).orElseThrow(ContestNotFoundException::new);
        Member member = memberDao.findById(memberId).orElseThrow(MemberNotFountException::new);

        Player player = Player.builder()
                .contest(contest)
                .member(member)
                .handicap(handicap)
                .build();
        playerDao.save(player);

        return player;
    }

    private PlayerDto convert(Player player) {
        return PlayerDto.builder()
                .id(player.getId())
                .number(player.getNumber())
                .handicap(player.getHandicap())
                .nickname(player.getMember().getNickname())
                .rank(player.getRank())
                .score(player.getScore())
                .build();
    }
}
