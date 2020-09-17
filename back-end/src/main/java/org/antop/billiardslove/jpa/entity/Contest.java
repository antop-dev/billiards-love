package org.antop.billiardslove.jpa.entity;

import lombok.*;
import org.antop.billiardslove.jpa.convertor.ProgressStatusConverter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TBL_CNTS")
public class Contest {

    @Id
    @Column(name = "CNTS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "CNTS_NM")
    private String title;

    @Setter
    @Column(name = "CNTS_DSCR")
    private String description;

    @Setter
    @Column(name = "STRT_DATE")
    private LocalDate startDate;

    @Setter
    @Column(name = "STRT_TIME")
    private LocalTime startTime;

    @Setter
    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Setter
    @Column(name = "END_TIME")
    private LocalTime endTime;

    @Setter
    @Convert(converter = ProgressStatusConverter.class)
    @Column(name = "PRGR_STT")
    private ProgressStatus progressStatus;

    @Setter
    @Column(name = "MAX_PRTC_PRSN")
    private int maximumParticipants;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RGST_USER_ID")
    private Manager registrationUser;

    @CreatedDate
    @Column(name = "RGST_DT")
    private LocalDateTime registerDateTime;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MDFY_USER_ID")
    private Manager modifyUser;

    @LastModifiedDate
    @Column(name = "MDFY_DT")
    private LocalDateTime modifyDateTime;


}
