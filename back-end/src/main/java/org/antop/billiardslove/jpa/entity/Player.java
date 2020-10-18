package org.antop.billiardslove.jpa.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TBL_PLYR")
public class Player {
    /**
     * 선수 아이디
     */
    @Id
    @Column(name = "PLYR_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 대회 아이디
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CNTS_ID")
    private Contest contest;
    /**
     * 회원 아이디
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MMBR_ID")
    private Member member;
    /**
     * 선수 번호
     */
    @Setter
    @Column(name = "PLYR_NO")
    private Integer number;
    /**
     * 참가 핸디캡
     */
    @Setter
    @Column(name = "PRTC_HNDC")
    private int handicap = 0;
    /**
     * 순위
     */
    @Setter
    @Column(name = "PLYR_RNKN")
    private Integer rank;
    /**
     * 점수
     */
    @Setter
    @Column(name = "PLYR_SCR")
    private int score = 0;
}
