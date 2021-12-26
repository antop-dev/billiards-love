package org.antop.billiardslove.jpa.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.antop.billiardslove.model.Outcome;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

/**
 * 선수 정보
 *
 * @author jammini
 */
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldNameConstants
@Entity
@Table(name = "tbl_plyr", uniqueConstraints = @UniqueConstraint(columnNames = {"cnts_id", "mmbr_id"}))
public class Player {
    /**
     * 선수 아이디
     */
    @Id
    @Column(name = "plyr_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 대회 아이디
     */
    @NotNull
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cnts_id")
    private Contest contest;
    /**
     * 회원 아이디
     */
    @NotNull
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mmbr_id")
    private Member member;
    /**
     * 선수 번호
     */
    @Setter
    @Column(name = "plyr_no")
    private Integer number;
    /**
     * 참가 핸디캡
     */
    @Setter
    @Column(name = "prtc_hndc")
    private int handicap = 0;
    /**
     * 순위
     */
    @Column(name = "plyr_rnkn")
    private Integer rank;
    /**
     * 점수
     */
    @Column(name = "plyr_scr")
    private int score;
    /**
     * 순위 변동 현황<br>
     * 양수: 올라감
     * 0: 그대로
     * 음수: 내려감
     */
    @Column(name = "plyr_vrtn")
    private int variation;

    public void setRank(int rank) {
        Integer oldRank = this.rank;
        this.rank = rank;
        this.variation = (oldRank == null) ? 0 : oldRank - rank;
    }

    @Builder
    protected Player(Contest contest, Member member, int handicap) {
        this.contest = contest;
        this.member = member;
        this.handicap = handicap;
    }

    /**
     * 선수 번호 부여<br>
     * 선수에게 선수 번호가 부여되면서 정보를 초기화 한다.
     *
     * @param number 선수 번호
     */
    public void assignNumber(int number) {
        this.number = number;
        this.score = 0;
        this.rank = null;
    }

    /**
     * 경기 결과로 점수 적용
     *
     * @param outcomes 경기 결과들
     */
    public void computeScore(Outcome[] outcomes) {
        score = Arrays.stream(outcomes)
                .map(Outcome::getScoreFunc)
                .reduce(this.score, (acc, func) -> func.apply(acc), Integer::sum);
    }

}
