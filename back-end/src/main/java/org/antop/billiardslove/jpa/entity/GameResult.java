package org.antop.billiardslove.jpa.entity;


import org.antop.billiardslove.jpa.convertor.BooleanToYNConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_GAME_RSLT")
public class GameResult {

    @Id
    @Column(name = "GAME_RSLT_ID")
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PLYR_ID")
    private Player player;

    @Column(name = "FRST_RSLT", nullable = false)
    private WinLoseStatus firstWinLose;

    @Column(name = "SCND_RSLT", nullable = false)
    private WinLoseStatus secondWinLose;

    @Column(name = "THRD_RSLT", nullable = false)
    private WinLoseStatus thirdWinLose;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "IS_CNFR")
    private boolean isConfirmation;

    @Column(name = "CNFR_DT", nullable = false)
    private LocalDateTime confirmationDateTime;
}
