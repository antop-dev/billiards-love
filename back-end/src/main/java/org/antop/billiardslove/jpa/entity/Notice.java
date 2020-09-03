package org.antop.billiardslove.jpa.entity;

import org.antop.billiardslove.jpa.convertor.BooleanToYNConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_NTC")
public class Notice {

    @Id
    @Column(name = "NTC_ID")
    @GeneratedValue
    private Long id;

    @Column(name = "NTC_TTL", nullable = false)
    private String title;

    @Column(name = "NTC_CNTN", nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "TRGT_CNTS_ID")
    private Contest contest;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "CAN_SKIP", nullable = false)
    private boolean canSkip;

    @ManyToOne
    @JoinColumn(name = "RGST_USER_ID")
    private Manager manager;

    @Column(name = "RGST_DT", nullable = false)
    private LocalDateTime registerDate;

    @ManyToOne
    @JoinColumn(name = "MDFT_USER_ID")
    private Manager managerModifyId;

    @Column(name = "MDFY_DT")
    private LocalDateTime modifyDate;

}
