package org.antop.billiardslove.jpa.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.antop.billiardslove.core.GameResultStatus;
import org.antop.billiardslove.jpa.convertor.GameResultStatusConverter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 게임 결과 확정
 *
 * @author jammini
 */
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TBL_GAME_RSLT_CNFR")
public class GameResultConfirmation {
    /**
     * 경기 결과 확정 아이디
     */
    @Id
    @Column(name = "GAME_RSLT_CNFR_ID")
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 선수 아이디
     */
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLYR_ID")
    private Player player;
    /**
     * 선수 입력 아이디
     */
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLYR_INPT_ID")
    private GameResultInput playerGameResultInput;
    /**
     * 상대 선수 아이디
     */
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPNN_PLYR_ID")
    private Player opponentPlayer;
    /**
     * 상대 선수 입력 아이디
     */
    @ToString.Exclude
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
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CNFR_MNGR_ID")
    private Manager manager;
    /**
     * 확정 일시
     */
    @LastModifiedDate
    @Column(name = "CNFR_DT")
    private LocalDateTime confirmationDateTime;

    @Builder
    public GameResultConfirmation(Player player, GameResultInput playerGameResultInput, Player opponentPlayer, GameResultInput opponentGameResultInput, GameResultStatus firstResult, GameResultStatus secondResult, GameResultStatus thirdResult, Manager manager){
        this.player = player;
        this.playerGameResultInput = playerGameResultInput;
        this.opponentPlayer = opponentPlayer;
        this.opponentGameResultInput = opponentGameResultInput;
        this.firstResult = firstResult;
        this.secondResult = secondResult;
        this.thirdResult = thirdResult;
        this.manager = manager;
    }
}
