package org.antop.billiardslove.jpa.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.antop.billiardslove.exception.AlreadyContestEndException;
import org.antop.billiardslove.exception.CantEndContestStateException;
import org.antop.billiardslove.exception.CantStartContestStateException;
import org.antop.billiardslove.exception.CantStopContestStateException;
import org.antop.billiardslove.jpa.convertor.ContestStateConverter;
import org.antop.billiardslove.model.ContestState;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 대회 정보
 *
 * @author jammini
 */
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldNameConstants
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tbl_cnts")
public class Contest {
    /**
     * 대회 아이디
     */
    @Id
    @Column(name = "cnts_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 대회명
     */
    @NotNull
    @Setter
    @Column(name = "cnts_nm", length = 100)
    private String title;
    /**
     * 대회 설명
     */
    @Setter
    @Column(name = "cnts_dscr")
    private String description;
    /**
     * 시작일
     */
    @Setter
    @Column(name = "strt_date")
    private LocalDate startDate;
    /**
     * 시작시간
     */
    @Setter
    @Column(name = "strt_time")
    private LocalTime startTime;
    /**
     * 종료일
     */
    @Setter
    @Column(name = "end_date")
    private LocalDate endDate;
    /**
     * 종료시간
     */
    @Setter
    @Column(name = "end_time")
    private LocalTime endTime;
    /**
     * 진행상태
     */
    @NotNull
    @Convert(converter = ContestStateConverter.class)
    @Column(name = "prgr_stt")
    private ContestState state = ContestState.PREPARING;
    /**
     * 최대 참가 인원
     */
    @Setter
    @Column(name = "max_prtc_prsn")
    private Integer maxJoiner;
    /**
     * 현재 참가 인원
     */
    @Setter
    @Column(name = "crnt_prsn")
    private int currentJoiner;
    /**
     * 대회 진행 률
     */
    @Setter
    @Column(name = "cnts_prgr")
    private double progress;
    /**
     * 등록 일시
     */
    @NotNull
    @CreatedDate
    @Column(name = "rgst_dt")
    private LocalDateTime created;
    /**
     * 마지막 수정 일시
     */
    @LastModifiedDate
    @Column(name = "mdfy_dt")
    private LocalDateTime modified;

    @Builder
    private Contest(String title, String description,
                    LocalDate startDate, LocalTime startTime,
                    LocalDate endDate, LocalTime endTime,
                    Integer maxJoiner, int currentJoiner,
                    double progress) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.maxJoiner = maxJoiner;
        this.currentJoiner = currentJoiner;
        this.progress = progress;
    }

    /**
     * 접수중 상태 여부
     *
     * @return 접수중
     */
    public boolean isAccepting() {
        return state == ContestState.ACCEPTING;
    }

    /**
     * 대회를 시작(재시작)할 수 있는 지 여부
     *
     * @return {@code true} 시작(재시작) 가능
     */
    public boolean canStart() {
        return state == ContestState.ACCEPTING || state == ContestState.STOPPED;
    }

    /**
     * 대회를 중지할 수 있는지 여부
     *
     * @return {@code true} 중지 가능
     */
    public boolean canStop() {
        return state == ContestState.PROCEEDING;
    }

    /**
     * 종료 상태인지 여부
     *
     * @return {@code true} 종료
     */
    public boolean isEnd() {
        return state == ContestState.END;
    }

    /**
     * 종료할 수 있는 지 여부
     *
     * @return {@code true} 종료 가능
     */
    public boolean canEnd() {
        return !isEnd();
    }

    /**
     * 대회 중지
     */
    public void stop() {
        if (!canStop()) {
            throw new CantStopContestStateException();
        }
        state = ContestState.STOPPED;
    }

    /**
     * 대회 시작
     */
    public void start() {
        if (!canStart()) {
            throw new CantStartContestStateException();
        }
        state = ContestState.PROCEEDING;
    }

    /**
     * 대회 종료
     */
    public void end() {
        if (isEnd()) {
            throw new AlreadyContestEndException();
        }
        if (!canEnd()) {
            throw new CantEndContestStateException();
        }
        state = ContestState.END;
    }

    /**
     * 접수 시작
     */
    public void open() {
        state = ContestState.ACCEPTING;
    }

}
