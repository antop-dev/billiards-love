package org.antop.billiardslove.jpa.entity;

import javax.persistence.*;

@Entity
@Table(name = "TBL_PLYR")
public class Player {

    @Id
    @Column(name = "PLYR_ID")
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MMVR_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CNTS_ID")
    private Contest contest;

    @Column(name = "PLYR_NO")
    private int number;

    @Column(name = "PRIC_HNDC")
    private Byte handicap;

    @Column(name = "PLYR_RNKN")
    private int rank;

    @Column(name = "PLYR_SCR")
    private int score;
}
