package org.antop.billiardslove.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.antop.billiardslove.jpa.convertor.BooleanConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "TBL_NTC")
public class Notice {

    @Id
    @Column(name = "NTC_ID")
    @GeneratedValue
    private Long id;

    @Column(name = "NTC_TTL")
    private String title;

    @Column(name = "NTC_CNTN")
    private String contents;

    @ManyToOne
    @JoinColumn(name = "TRGT_CNTS_ID")
    private Contest contest;

    @Convert(converter = BooleanConverter.class)
    @Column(name = "CAN_SKIP")
    private boolean canSkip;

    @ManyToOne
    @JoinColumn(name = "RGST_USER_ID")
    private Manager manager;

    @Column(name = "RGST_DT")
    private LocalDateTime registerDateTime;

    @Column(name = "MDFT_USER_ID")
    private Long managerModifyId;

    @Column(name = "MDFY_DT")
    private LocalDateTime modifyDateTime;

}
