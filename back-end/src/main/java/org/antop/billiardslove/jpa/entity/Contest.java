package org.antop.billiardslove.jpa.entity;

import org.antop.billiardslove.jpa.convertor.BooleanToYNConverter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "TBL_CNTS")
public class Contest {

    @Id
    @Column(name = "CNTS_ID")
    @GeneratedValue
    private Long id;

    @Column(name = "CNTS_NM", nullable = false, length = 100)
    private String title;

    @Column(name = "CNTS_DSCR")
    private String explanation;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "START_TIME")
    private LocalTime startTime;

    @Column(name = "END_DATE", nullable = false)
    private LocalDate endDate;

    @Column(name = "END_TIME")
    private LocalTime endTime;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "PRGR_STT", nullable = false)
    private boolean progressStatus;

    @Column(name = "MAX_PRIC_PRSN")
    private int maximumParticipants;

}
