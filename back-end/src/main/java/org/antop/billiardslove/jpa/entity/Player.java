package org.antop.billiardslove.jpa.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "TBL_PLYR")
public class Player {

    @Id
    @Column(name = "PLYR_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CNTS_ID")
    private Contest contest;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MMBR_ID")
    private Member member;

    @Column(name = "PLYR_NO")
    private int number;

    @Column(name = "PRTC_HNDC")
    private int handicap;

    @Column(name = "PLYR_RNKN")
    private Integer rank;

    @Column(name = "PLYR_SCR")
    private Integer score;
}
