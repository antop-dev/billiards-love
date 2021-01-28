package org.antop.billiardslove.jpa.entity;

import lombok.*;
import org.antop.billiardslove.core.GameResultStatus;
import org.antop.billiardslove.jpa.convertor.GameResultStatusConverter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 게임 결과 입력
 *
 * @author jammini
 */
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TBL_GAME_RSLT_INPT")
public class GameResultInput {
    /**
     * 경기 결과 입력 아이디
     */
    @Id
    @Column(name = "GAME_RSLT_INPT_ID")
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
     * 상대 선수 아이디
     */
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPNN_PLYR_ID")
    private Player opponentPlayer;
    /**
     * 첫번째 경기 결과
     */
    @Setter
    @Convert(converter = GameResultStatusConverter.class)
    @Column(name = "FRST_RSLT")
    private GameResultStatus firstResult = GameResultStatus.NONE;
    /**
     * 두번째 경기 결과
     */
    @Setter
    @Convert(converter = GameResultStatusConverter.class)
    @Column(name = "SCND_RSLT")
    private GameResultStatus secondResult = GameResultStatus.NONE;
    /**
     * 세번째 경기 결과
     */
    @Setter
    @Convert(converter = GameResultStatusConverter.class)
    @Column(name = "THRD_RSLT")
    private GameResultStatus thirdResult = GameResultStatus.NONE;
    /**
     * 입력 일시
     */
    @LastModifiedDate
    @Column(name = "INPT_DT")
    private LocalDateTime inputDateTime;

    @Builder
    public GameResultInput(Player player, Player opponentPlayer) {
        this.player = player;
        this.opponentPlayer = opponentPlayer;
    }
}
