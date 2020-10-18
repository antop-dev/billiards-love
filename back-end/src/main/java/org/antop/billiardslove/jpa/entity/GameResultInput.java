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
@Table(name = "TBL_GAME_RSLT_INPT")
public class GameResultInput {
    /**
     * 경기 결과 입력 아이디
     */
    @Id
    @Column(name = "GAME_RSLT_INPT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 선수 아이디
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLYR_ID")
    private Player player;
    /**
     * 상대 선수 아이디
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPNN_PLYR_ID")
    private Player opponentPlayer;
    /**
     * 첫번째 경기 결과
     */
    @Setter
    @Convert(converter = GameResultStatusConverter.class)
    @Column(name = "FRST_RSLT")
    private GameResultStatus firstResult;
    /**
     * 두번째 경기 결과
     */
    @Setter
    @Convert(converter = GameResultStatusConverter.class)
    @Column(name = "SCND_RSLT")
    private GameResultStatus secondResult;
    /**
     * 세번째 경기 결과
     */
    @Setter
    @Convert(converter = GameResultStatusConverter.class)
    @Column(name = "THRD_RSLT")
    private GameResultStatus thirdResult;
    /**
     * 입력 일시
     */
    @Setter
    @Column(name = "INPT_DT")
    private LocalDateTime inputDateTime;
}
