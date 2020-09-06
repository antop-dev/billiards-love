package org.antop.billiardslove.jpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.antop.billiardslove.jpa.convertor.BooleanConverter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "TBL_NTC")
public class Notice {

    @Id
    @Column(name = "NTC_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "NTC_TTL")
    private String title;

    @Setter
    @Column(name = "NTC_CNTN")
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRGT_CNTS_ID")
    private Contest contest;

    @Setter
    @Convert(converter = BooleanConverter.class)
    @Column(name = "CAN_SKIP")
    private boolean canSkip;

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
