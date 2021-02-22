package org.antop.billiardslove.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 대회 순위 응답 클래스
 *
 * @author jammini
 */
@Getter
@AllArgsConstructor
@Builder
public class ContestRank {

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

    @Getter
    @Builder
    @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Participant {
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
