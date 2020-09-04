package org.antop.billiardslove.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.antop.billiardslove.jpa.convertor.BooleanConverter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "TBL_CNTS")
public class Contest {

    @Id
    @Column(name = "CNTS_ID")
    @GeneratedValue
    private Long id;

    @Column(name = "CNTS_NM")
    private String title;

    @Column(name = "CNTS_DSCR")
    private String description;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "START_TIME")
    private LocalTime startTime;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "END_TIME")
    private LocalTime endTime;

    @Convert(converter = BooleanConverter.class)
    @Column(name = "PRGR_STT")
    private boolean progressStatus;

    @Column(name = "MAX_PRIC_PRSN")
    private int maximumParticipants;

}
