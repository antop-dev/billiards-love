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

/**
 * 대회 정보
 *
 * @author jammini
 */
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TBL_CNTS")
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
    @Column(name = "CNTS_NM")
    private String title;
    /**
     * 대회 설명
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
    private ProgressStatus progressStatus = ProgressStatus.ACCEPTING;
    /**
     * 최대 참가 인원<br>
     * 0일 경우 무제한
     */
    @Setter
    @Column(name = "MAX_PRTC_PRSN")
    private int maximumParticipants = 0;
    /**
     * 등록 관리자 아이디
     */
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RGST_MNGR_ID")
    private Manager registrationUser;
    /**
     * 등록 일시
     */
    @CreatedDate
    @Column(name = "RGST_DT")
    private LocalDateTime registerDateTime;
    /**
     * 수정 관리자 아이디
     */
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MDFY_MNGR_ID")
    private Manager modifyUser;
    /**
     * 수정 일시
     */
    @LastModifiedDate
    @Column(name = "MDFY_DT")
    private LocalDateTime modifyDateTime;

    @Builder
    public Contest(String title, String description, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime, int maximumParticipants, Manager registrationUser, Manager modifyUser) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.maximumParticipants = maximumParticipants;
        this.registrationUser = registrationUser;
        this.modifyUser = modifyUser;
    }

}
