package org.antop.billiardslove.jpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "TBL_PLYR")
public class Player {

    @Id
    @Column(name = "PLYR_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CNTS_ID")
    private Contest contest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MMBR_ID")
    private Member member;

    @Column(name = "PLYR_NO")
    private int number;

    @Column(name = "PRIC_HNDC")
    private int handicap;

    @Column(name = "PLYR_RNKN")
    private Integer rank;

    @Column(name = "PLYR_SCR")
    private Integer score;
}
