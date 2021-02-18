package org.antop.billiardslove.api;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 대회 순위 응답 클래스
 *
 * @author jammini
 */
@Getter
public class ContestRankResponse {

    /**
     * 순위
     */
    private final Integer rank;
    /**
     * 참가자
     */
    private final Participant participant;
    /**
     * 진행률
     */
    private final Integer progress;
    /**
     * 점수
     */
    private final Integer score;

    @Builder
    public ContestRankResponse(Integer rank, Participant participant, Integer progress, Integer score) {
        this.rank = rank;
        this.participant = participant;
        this.progress = progress;
        this.score = score;
    }

    @Getter
    @Builder
    @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
    static class Participant {
        /**
         * 참가자 아이디
         */
        private final long id;
        /**
         * 참가자명
         */
        private final String name;
        /**
         * 참가자 핸디캡
         */
        private final Integer handicap;
    }

}
