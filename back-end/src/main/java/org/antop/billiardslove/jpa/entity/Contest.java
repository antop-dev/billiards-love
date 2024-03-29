package org.antop.billiardslove.jpa.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.antop.billiardslove.exception.ContestAcceptingException;
import org.antop.billiardslove.exception.ContestEndException;
import org.antop.billiardslove.exception.ContestMaxJoinerException;
import org.antop.billiardslove.exception.ContestPreparingException;
import org.antop.billiardslove.exception.ContestProceedingException;
import org.antop.billiardslove.exception.ContestStoppedException;
import org.antop.billiardslove.model.ContestState;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
    @Column(name = "prgr_stt")
    @Enumerated(EnumType.STRING)
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
    protected Contest(String title, String description,
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
     * 대회 중지
     */
    public void stop() {
        if (isEnd()) throw new ContestEndException();
        state = ContestState.STOPPED;
    }

    /**
     * 대회 시작
     */
    public void start() {
        if (isPreparing()) throw new ContestPreparingException();
        if (isEnd()) throw new ContestEndException();
        if (isProceeding()) throw new ContestProceedingException();
        state = ContestState.PROCEEDING;
    }

    /**
     * 대회 종료<br>
     * 준비중/중지된 대회는 종료 가능
     */
    public void end() {
        if (isAccepting()) throw new ContestAcceptingException();
        if (isProceeding()) throw new ContestProceedingException();
        if (isEnd()) throw new ContestEndException();
        state = ContestState.END;
    }

    /**
     * 접수 시작
     */
    public void open() {
        if (isProceeding()) throw new ContestProceedingException();
        if (isEnd()) throw new ContestEndException();
        if (isStopped()) throw new ContestStoppedException();
        state = ContestState.ACCEPTING;
    }

    /**
     * 준비중 상태 여부
     */
    public boolean isPreparing() {
        return state == ContestState.PREPARING;
    }

    /**
     * 접수중 상태 여부
     */
    public boolean isAccepting() {
        return state == ContestState.ACCEPTING;
    }

    /**
     * 진행중 상태 여부
     */
    public boolean isProceeding() {
        return state == ContestState.PROCEEDING;
    }

    /**
     * 중지 상태 여부
     */
    public boolean isStopped() {
        return state == ContestState.STOPPED;
    }

    /**
     * 종료 상태 여부
     */
    public boolean isEnd() {
        return state == ContestState.END;
    }

    /**
     * 참가자 수 증가
     */
    public void incrementJoiner() {
        if (currentJoiner >= maxJoiner) throw new ContestMaxJoinerException(maxJoiner);
        currentJoiner++;
    }
}
