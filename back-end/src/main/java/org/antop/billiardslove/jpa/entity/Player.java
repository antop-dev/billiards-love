package org.antop.billiardslove.jpa.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

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
@Table(name = "tbl_plyr", uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"plyr_id", "cnts_id", "mmbr_id"}
        )
})
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
    @Setter
    @Column(name = "plyr_rnkn")
    private Integer rank;
    /**
     * 점수
     */
    @Setter
    @Column(name = "plyr_scr")
    private Integer score;

    @Builder
    private Player(Contest contest, Member member, int handicap) {
        this.contest = contest;
        this.member = member;
        this.handicap = handicap;
    }

}
