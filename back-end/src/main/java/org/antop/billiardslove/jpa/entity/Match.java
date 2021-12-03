package org.antop.billiardslove.jpa.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.antop.billiardslove.exception.NotJoinedMatchException;
import org.antop.billiardslove.jpa.convertor.MatchResultConverter;
import org.antop.billiardslove.model.MatchResult;
import org.antop.billiardslove.model.Outcome;

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
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@FieldNameConstants
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_mtc")
public class Match {
    /**
     * 매칭 아이디
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mtc_id")
    private Long id;

    /**
     * 대회 아이디
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "cnts_id")
    @ToString.Exclude
    private Contest contest;

    /**
     * 선수1 아이디
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "plyr1_id")
    @ToString.Exclude
    private Player player1;

    /**
     * 선수2 아이디
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "plyr2_id")
    @ToString.Exclude
    private Player player2;

    /**
     * 선수1이 입력한 경기 결과
     */
    @NotNull
    @Convert(converter = MatchResultConverter.class)
    @Column(name = "plyr1_rslt_inpt")
    private MatchResult matchResult1 = MatchResult.NONE;

    /**
     * 선수2가 입력한 경기 결과
     */
    @NotNull
    @Convert(converter = MatchResultConverter.class)
    @Column(name = "plyr2_rslt_inpt")
    private MatchResult matchResult2 = MatchResult.NONE;

    /**
     * 확정 멤버 아이디
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cnfr_mmbr_id")
    private Member manager;

    /**
     * 확정 일시
     */
    @Column(name = "cnfr_dt")
    private LocalDateTime confirmAt;

    @Builder
    protected Match(Contest contest, Player player1, Player player2) {
        this.contest = contest;
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * 해당 선수의 매칭 결과를 입력한다.
     *
     * @param memberId 회원 아이디
     * @param first    첫번째 경기 결과
     * @param second   두번째 경기 결과
     * @param third    세번째 경기 결과
     */
    public void enterResult(long memberId, Outcome first, Outcome second, Outcome third) {
        Player me = getMe(memberId);
        if (me == player1) {
            matchResult1 = MatchResult.of(first, second, third);
        } else {
            matchResult2 = MatchResult.of(first, second, third);
        }
    }

    /**
     * 회원의 매칭 결과를 가져온다.
     *
     * @param memberId 회원 아이디
     * @return 경기 결과
     * @throws NotJoinedMatchException 경기에 참여하지 않은 회원
     */
    public MatchResult getResult(long memberId) {
        Player p = getMe(memberId);
        return p == player1 ? matchResult1 : matchResult2;
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
     * 나를 찾는다.
     *
     * @param memberId 회원 아이디
     * @return 내 참가자 정보
     * @throws NotJoinedMatchException 경기에 참여하지 않은 회원
     */
    public Player getMe(long memberId) {
        if (player1.getMember().getId() == memberId) {
            return player1;
        } else if (player2.getMember().getId() == memberId) {
            return player2;
        } else {
            throw new NotJoinedMatchException();
        }
    }

    /**
     * 상대 참가자를 찾는다.
     *
     * @param memberId 회원 아이디
     * @return 상대 참가자
     * @throws NotJoinedMatchException 경기에 참여하지 않은 회원
     */
    public Player getOpponent(long memberId) {
        return getMe(memberId) == player1 ? player2 : player1;
    }

    /**
     * 관리자에 의해 확정이 된 경기인지 여부
     *
     * @return {@code 확정됨}
     */
    public boolean isConfirmed() {
        return manager != null && confirmAt != null;
    }

}
