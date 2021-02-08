package org.antop.billiardslove.jpa.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.antop.billiardslove.core.ProgressStatus;
import org.antop.billiardslove.jpa.convertor.ProgressStatusConverter;
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
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tbl_cnts")
public class Contest {
    /**
     * 대회 아이디
     */
    @Id
    @Column(name = "CNTS_ID")
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 대회명
     */
    @Setter
    @Column(name = "cnts_nm")
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
    @Setter
    @Convert(converter = ProgressStatusConverter.class)
    @Column(name = "prgr_stt")
    private ProgressStatus progressStatus = ProgressStatus.ACCEPTING;
    /**
     * 최대 참가 인원
     */
    @Setter
    @Column(name = "max_prtc_prsn")
    private Integer maximumParticipants;

    @CreatedDate
    @Column(name = "rgst_dt")
    private LocalDateTime created;

    @LastModifiedDate
    @Column(name = "mdfy_dt")
    private LocalDateTime modified;

    @Builder
    private Contest(String title) {
        this.title = title;
    }

}
