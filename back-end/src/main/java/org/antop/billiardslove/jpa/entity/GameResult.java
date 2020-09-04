package org.antop.billiardslove.jpa.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.antop.billiardslove.jpa.convertor.BooleanConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter @ToString
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

    @Column(name = "FRST_RSLT")
    private GameResultStatus firstResult;

    @Column(name = "SCND_RSLT")
    private GameResultStatus secondResult;

    @Column(name = "THRD_RSLT")
    private GameResultStatus thirdResult;

    @Convert(converter = BooleanConverter.class)
    @Column(name = "IS_CNFR")
    private boolean confirmation;

    @Column(name = "CNFR_DT")
    private LocalDateTime confirmationDateTime;
}
