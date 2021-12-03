package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.DataJpaBase;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Match;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.entity.Player;
import org.antop.billiardslove.model.MatchResult;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Optional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresent;
import static org.antop.billiardslove.model.Outcome.LOSE;
import static org.antop.billiardslove.model.Outcome.NONE;
import static org.antop.billiardslove.model.Outcome.WIN;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

class MatchRepositoryTest extends DataJpaBase {
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ContestRepository contestRepository;

    @Test
    @DisplayName("시합 정보를 저장한다.")
    void save() {
        // 1. 데이터 준비
        Contest contest = contest();
        contestRepository.save(contest);

        Member member1 = member();
        Member member2 = member();
        memberRepository.saveAll(Arrays.asList(member1, member2));

        Player player1 = player(contest, member1);
        Player player2 = player(contest, member2);
        playerRepository.saveAll(Arrays.asList(player1, player2));

        Match match = match(contest, player1, player2);
        MatcherAssert.assertThat(match.getId(), nullValue());
        matchRepository.save(match);

        flushAndClear();

        // 2. 실행
        Optional<Match> optional = matchRepository.findById(match.getId());

        // 3. 검증
        assertThat(optional, isPresent());
        optional.ifPresent(it -> assertThat(it.getId(), notNullValue()));
    }

    @Test
    @DisplayName("시합정보를 변경한다?")
    void change() {
        // 1. 데이터 준비
        Contest contest = contest();
        contestRepository.save(contest);

        Member member1 = member();
        Member member2 = member();
        memberRepository.saveAll(Arrays.asList(member1, member2));

        Player player1 = player(contest, member1);
        Player player2 = player(contest, member2);
        playerRepository.saveAll(Arrays.asList(player1, player2));

        Match match = match(contest, player1, player2);
        matchRepository.save(match);
        flushAndClear();

        // 2. 실행
        Member admin = member();
        admin.setManager(true);
        memberRepository.save(admin);

        match.enterResult(member1.getId(), WIN, LOSE, NONE);
        match.enterResult(member2.getId(), LOSE, LOSE, NONE);
        match.decide(admin);
        matchRepository.save(match);

        flushAndClear();

        // 3. 검증
        Optional<Match> optional = matchRepository.findById(match.getId());
        assertThat(optional.isPresent(), is(true));
        optional.ifPresent(it -> {
            assertThat(it.getResult(member1.getId()), is(MatchResult.of(WIN, LOSE, NONE)));
            assertThat(it.getResult(member2.getId()), is(MatchResult.of(LOSE, LOSE, NONE)));
            assertThat(it.isConfirmed(), is(true));
        });
    }

}
