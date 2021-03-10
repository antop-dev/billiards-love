package org.antop.billiardslove.api;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.jpa.entity.Player;
import org.antop.billiardslove.service.MatchGetService;
import org.antop.billiardslove.service.MatchGetService.MyMatch;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 대진표 조회 API
 *
 * @author antop
 */
@RequiredArgsConstructor
@RestController
public class MatchInfoApi {
    private final MatchGetService matchGetService;

    @GetMapping("/api/v1/contest/{id}/matches")
    public List<Match> matches(@PathVariable("id") final long contestId,
                               @AuthenticationPrincipal final Long memberId) {
        // 참가자 정보와 매칭 정보를 같이 가져온다.
        final MyMatch myMatch = matchGetService.getMatches(contestId, memberId);
        return myMatch.getMatches()
                .stream()
                .map(it -> convert(it, myMatch.getPlayer()))
                // 상대방 참가자의 번호로 오름차순 정렬
                .sorted(Comparator.comparingLong(o -> o.getOpponent().getNumber()))
                .collect(Collectors.toList());
    }

    private Match convert(final org.antop.billiardslove.jpa.entity.Match match, final Player player) {
        final Player opponent = match.getOpponent(player);
        return Match.builder()
                .id(match.getId())
                .opponent(Opponent.builder()
                        .id(opponent.getId())
                        .number(opponent.getNumber())
                        .nickname(opponent.getMember().getNickname())
                        .build())
                .result(Arrays.stream(match.getMatchResult(player).toArray())
                        .map(Enum::name)
                        .toArray(String[]::new))
                .confirmed(match.isConfirmed())
                .build();
    }

    @RequiredArgsConstructor
    @Builder
    @Getter
    static class Match {
        /**
         * 매칭 아이디
         */
        private final long id;
        /**
         * 상대 참가자 정보
         */
        private final Opponent opponent;
        /**
         * 경기 결과
         */
        private final String[] result;
        /**
         * 확정 여부
         */
        private final boolean confirmed;
    }

    @RequiredArgsConstructor
    @Builder
    @Getter
    static class Opponent {
        /**
         * 참가자 아이디
         */
        private final long id;
        /**
         * 참가자 번호 (순번)
         */
        private final long number;
        /**
         * 참가자명 (별명)
         */
        private final String nickname;
    }

}
