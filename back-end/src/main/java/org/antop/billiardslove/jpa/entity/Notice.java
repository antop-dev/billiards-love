package org.antop.billiardslove.jpa.entity;

import lombok.*;
import org.antop.billiardslove.jpa.convertor.BooleanConverter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@EntityListeners(AuditingEntityListener.class)
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

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRGT_CNTS_ID")
    private Contest contest;

    @Setter
    @Convert(converter = BooleanConverter.class)
    @Column(name = "CAN_SKIP")
    private boolean canSkip;

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
