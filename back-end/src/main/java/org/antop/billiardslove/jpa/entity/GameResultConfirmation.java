package org.antop.billiardslove.jpa.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.antop.billiardslove.core.GameResultStatus;
import org.antop.billiardslove.jpa.convertor.GameResultStatusConverter;

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
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TBL_GAME_RSLT_CNFR")
public class GameResultConfirmation {
    /**
     * 경기 결과 확정 아이디
     */
    @Id
    @Column(name = "GAME_RSLT_CNFR_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 선수아이디
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLYR_ID")
    private Player player;
    /**
     * 선수 입력 아이디
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLYR_INPT_ID")
    private GameResultInput playerGameResultInput;
    /**
     * 상대선수 아이디
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPNN_PLYR_ID")
    private Player opponentPlayer;
    /**
     * 상대 선수 입력 아이디
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPNN_PLYR_INPT_ID")
    private GameResultInput opponentGameResultInput;
    /**
     * 첫 번째 경기 결과
     */
    @Setter
    @Convert(converter = GameResultStatusConverter.class)
    @Column(name = "FRST_RSLT")
    private GameResultStatus firstResult;
    /**
     * 두 번째 경기 결과
     */
    @Setter
    @Convert(converter = GameResultStatusConverter.class)
    @Column(name = "SCND_RSLT")
    private GameResultStatus secondResult;
    /**
     * 세 번째 경기 결과
     */
    @Setter
    @Convert(converter = GameResultStatusConverter.class)
    @Column(name = "THRD_RSLT")
    private GameResultStatus thirdResult;
    /**
     * 확정자 아이디
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CNFR_MNGR_ID")
    private Manager manager;
    /**
     * 확정 일시
     */
    @Setter
    @Column(name = "CNFR_DT")
    private LocalDateTime confirmationDateTime;
}
