package org.antop.billiardslove.jpa.entity;

import lombok.*;
import org.antop.billiardslove.jpa.convertor.BooleanConverter;
import org.antop.billiardslove.jpa.convertor.GameResultStatusConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "TBL_GAME_RSLT")
public class GameResult {

    @Id
    @Column(name = "GAME_RSLT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLYR_ID")
    private Player player;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPNN_PLYR_ID")
    private Player opponentPlayer;

    @Setter
    @Convert(converter = GameResultStatusConverter.class)
    @Column(name = "FRST_RSLT")
    private GameResultStatus firstResult;

    @Setter
    @Convert(converter = GameResultStatusConverter.class)
    @Column(name = "SCND_RSLT")
    private GameResultStatus secondResult;

    @Setter
    @Convert(converter = GameResultStatusConverter.class)
    @Column(name = "THRD_RSLT")
    private GameResultStatus thirdResult;

    @Setter
    @Convert(converter = BooleanConverter.class)
    @Column(name = "IS_CNFR")
    private boolean confirmation;

    @Setter
    @Column(name = "CNFR_DT")
    private LocalDateTime confirmationDateTime;
}
