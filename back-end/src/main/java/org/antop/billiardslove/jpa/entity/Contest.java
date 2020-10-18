package org.antop.billiardslove.jpa.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TBL_CNTS")
public class Contest {
    /**
     * 대회 아이디
     */
    @Id
    @Column(name = "CNTS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 대회명
     */
    @Setter
    @Column(name = "CNTS_NM")
    private String title;
    /**
     * 대회설명
     */
    @Setter
    @Column(name = "CNTS_DSCR")
    private String description;
    /**
     * 시작일
     */
    @Setter
    @Column(name = "STRT_DATE")
    private LocalDate startDate;
    /**
     * 시작시간
     */
    @Setter
    @Column(name = "STRT_TIME")
    private LocalTime startTime;
    /**
     * 종료일
     */
    @Setter
    @Column(name = "END_DATE")
    private LocalDate endDate;
    /**
     * 종료시간
     */
    @Setter
    @Column(name = "END_TIME")
    private LocalTime endTime;
    /**
     * 진행상태
     */
    @Setter
    @Convert(converter = ProgressStatusConverter.class)
    @Column(name = "PRGR_STT")
    private ProgressStatus progressStatus;
    /**
     * 최대 참가 인원
     */
    @Setter
    @Column(name = "MAX_PRTC_PRSN")
    private int maximumParticipants;
    /**
     * 등록 관리자 아이디
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RGST_MNGR_ID")
    private Manager registrationUser;
    /**
     * 등록일시
     */
    @CreatedDate
    @Column(name = "RGST_DT")
    private LocalDateTime registerDateTime;
    /**
     * 수정 관리자 아이디
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MDFY_MNGR_ID")
    private Manager modifyUser;
    /**
     * 수정 일시
     */
    @LastModifiedDate
    @Column(name = "MDFY_DT")
    private LocalDateTime modifyDateTime;
}
