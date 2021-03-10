package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.exception.ContestNotFoundException;
import org.antop.billiardslove.exception.MemberNotFountException;
import org.antop.billiardslove.jpa.entity.Contest;
import org.antop.billiardslove.jpa.entity.Match;
import org.antop.billiardslove.jpa.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

class MatchRepositoryTest extends SpringBootBase {
    @Autowired
    private ContestRepository contestRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MatchRepository matchRepository;

    @Test
    void findInMe() {
        Contest contest = contestRepository.findById(1L).orElseThrow(ContestNotFoundException::new);
        Member member = memberRepository.findById(2L).orElseThrow(MemberNotFountException::new);

        List<Match> matches = matchRepository.findParticipated(contest, member);
        assertThat(matches, hasSize(4));
    }
}
