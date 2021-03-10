package org.antop.billiardslove.service;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.jpa.entity.Match;
import org.antop.billiardslove.jpa.entity.Player;

import java.util.List;

/**
 * 경기 조회 관련 서비스
 *
 * @author antop
 */
public interface MatchGetService {

    /**
     * 대회에서 나(회원)를 기준으로 상대방 대진표를 조회한다.
     *
     * @param contestId 대회 아이디
     * @param memberId  회원 아이디
     * @return 대진표 정보
     */
    MyMatch getMatches(long contestId, long memberId);

    @Getter
    @RequiredArgsConstructor
    @Builder
    class MyMatch {
        /**
         * 내 참가자 정보
         */
        private final Player player;
        /**
         * 내 경기 목록
         */
        private final List<Match> matches;
    }

}
