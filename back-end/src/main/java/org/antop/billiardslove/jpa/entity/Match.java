package org.antop.billiardslove.jpa.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.antop.billiardslove.jpa.convertor.MatchResultConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Arrays;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Entity
@Table(name = "tbl_mtc")
public class Match {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mtc_id")
    private Long id;

    /**
     * 대회
     */
    @Getter
    @ManyToOne
    @JoinColumn(name = "cnts_id")
    @ToString.Exclude
    private Contest contest;

    /**
     * 선수1
     */
    @ManyToOne
    @JoinColumn(name = "plyr1_id")
    @ToString.Exclude
    private Player player1;

    /**
     * 선수2
     */
    @ManyToOne
    @JoinColumn(name = "plyr2_id")
    @ToString.Exclude
    private Player player2;

    /**
     * 선수1이 입력한 경기 결과
     */
    @Convert(converter = MatchResultConverter.class)
    @Column(name = "plyr1_rslt_inpt")
    private MatchResult matchResult1;

    /**
     * 선수2가 입력한 경기 결과
     */
    @Convert(converter = MatchResultConverter.class)
    @Column(name = "plyr2_rslt_inpt")
    private MatchResult matchResult2;

    /**
     * 확정 정보
     */
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cnfr_mmbr_id")
    private Member manager;

    @Getter
    @Column(name = "cnfr_dt")
    private LocalDateTime confirmAt;

    @Builder
    private Match(Contest contest, Player player1, Player player2) {
        this.contest = contest;
        this.player1 = player1;
        this.player2 = player2;
        this.matchResult1 = MatchResult.NONE;
        this.matchResult2 = MatchResult.NONE;
    }

    /**
     * 해당 선수의 매칭 결과를 입력한다.
     *
     * @param player 선수
     * @param first  첫번째 경기 결과
     * @param second 두번째 경기 결과
     * @param third  세번째 경기 결과
     */
    public void enterResult(Player player, Result first, Result second, Result third) {
        if (player == player1) {
            matchResult1 = new MatchResult(first, second, third);
        } else if (player == player2) {
            matchResult2 = new MatchResult(first, second, third);
        } else {
            throw new IllegalArgumentException("player not found.");
        }
    }

    /**
     * 해당 선수의 매칭 결과를 조회한다.
     *
     * @param player 선수
     * @return {@link MatchResult}
     */
    public MatchResult getMatchResult(Player player) {
        if (player == player1) {
            return matchResult1;
        } else if (player == player2) {
            return matchResult2;
        } else {
            throw new IllegalArgumentException("player not found.");
        }
    }

    /**
     * 경기 결과를 확정 짓는다.
     *
     * @param manager 매니저 권한의 맴버
     */
    public void decide(Member manager) {
        if (!manager.isManager()) throw new IllegalArgumentException("not manager");
        this.manager = manager;
        this.confirmAt = LocalDateTime.now();
    }

    /**
     * 상대 참가자를 찾는다.
     *
     * @param player 나 자신
     * @return 상대 참가자
     */
    public Player getOpponent(Player player) {
        return player == player1 ? player2 : player1;
    }

    /**
     * 관리자에 의해 확정이 된 경기인지 여부
     *
     * @return {@code 확정됨}
     */
    public boolean isConfirmed() {
        return manager != null && confirmAt != null;
    }

    /**
     * 입력된 결과
     *
     * @author antop
     */
    @Getter
    @EqualsAndHashCode
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class MatchResult {
        /**
         * 결과가 입력되지 않은 경기 결과
         */
        public final static MatchResult NONE = MatchResult.of(Result.NONE, Result.NONE, Result.NONE);
        /**
         * 첫번째 경기
         */
        private final Result first;
        /**
         * 두번째 경기
         */
        private final Result second;
        /**
         * 세번째 경기
         */
        private final Result third;

        /**
         * 배열로 리턴
         *
         * @return 크기 3의 배열
         */
        public Result[] toArray() {
            return new Result[]{first, second, third};
        }

        public static MatchResult of(Result first, Result second, Result third) {
            return new MatchResult(first, second, third);
        }

        @Override
        public String toString() {
            return Arrays.toString(toArray());
        }
    }


    /**
     * 한 경기의 결과
     *
     * @author antop
     */
    @AllArgsConstructor
    @Getter
    public enum Result {
        /**
         * 승
         */
        WIN("W"),
        /**
         * 패
         */
        LOSE("L"),
        /**
         * 기권
         */
        ABSTENTION("A"),
        /**
         * 진행의사 있음
         */
        HOLD("H"),
        /**
         * 입력되지 않음
         */
        NONE("N");

        private final String code;

        public static Result of(String code) {
            return Arrays.stream(values())
                    .filter(it -> it.code.equals(code))
                    .findFirst()
                    .orElseThrow(IllegalAccessError::new);
        }
    }

}
