package org.antop.billiardslove.jpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.antop.billiardslove.jpa.convertor.ProgressStatusConverter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@ToString
@NoArgsConstructor
@Entity
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
    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Setter
    @Column(name = "START_TIME")
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
    private boolean progressStatus;

    @Setter
    @Column(name = "MAX_PRIC_PRSN")
    private int maximumParticipants;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RGST_USER_ID")
    private Manager registrationUser;

    @CreatedDate
    @Column(name = "RGST_DT")
    private LocalDateTime registerDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MDFT_USER_ID")
    private Manager modifyUser;

    @LastModifiedDate
    @Column(name = "MDFY_DT")
    private LocalDateTime modifyDateTime;


}
