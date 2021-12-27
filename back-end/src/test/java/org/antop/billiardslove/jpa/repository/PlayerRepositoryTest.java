package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.DataJpaBase;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Member;
import org.antop.billiardslove.jpa.entity.Player;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty;
import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresent;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

class PlayerRepositoryTest extends DataJpaBase {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ContestRepository contestRepository;

    @Test
    @DisplayName("플레이어 정보를 저장한다.")
    void save() {
        final Member member = member();
        memberRepository.save(member);

        final Contest contest = contest();
        contestRepository.save(contest);

        final Player player = player(contest, member);
        playerRepository.save(player);

        flushAndClear();

        // 2. 실행
        Optional<Player> optional = playerRepository.findById(player.getId());

        // 3. 검증
        assertThat(optional, isPresent());
        optional.ifPresent(it -> assertThat(it.getHandicap(), is(player.getHandicap())));
    }

    @Test
    @DisplayName("플레이어 정보를 변경한다.")
    void change() {
        // 1. 데이터 준비
        final Member member = member();
        memberRepository.save(member);

        final Contest contest = contest();
        contestRepository.save(contest);

        final Player player = player(contest, member);
        playerRepository.save(player);

        flushAndClear();

        // 2. 실행
        final int newNumber = 1;
        final int newHandicap = 70;

        playerRepository.findById(player.getId()).ifPresent(it -> {
            it.setNumber(newNumber);
            it.setHandicap(newHandicap);
            playerRepository.save(it);
            flushAndClear();
        });

        // 3. 검증
        Optional<Player> optional = playerRepository.findById(player.getId());
        assertThat(optional, isPresent());
        optional.ifPresent(it -> {
            assertThat(it.getHandicap(), is(newHandicap));
            assertThat(it.getNumber(), is(newNumber));
            assertThat(it.getRank(), nullValue());
            assertThat(it.getScore(), is(0));
        });
    }

    @Test
    @DisplayName("삭제된 플레이어를 조회한다.")
    void delete() {
        // 1. 데이터 준비
        Member member = member();
        memberRepository.save(member);

        Contest contest = contest();
        contestRepository.save(contest);

        Player player = player(contest, member);
        playerRepository.save(player);

        flushAndClear();

        // 2. 실행
        playerRepository.deleteById(player.getId());
        flushAndClear();

        // 3. 검증
        Optional<Player> optional = playerRepository.findById(player.getId());
        assertThat(optional, isEmpty());
    }
}
