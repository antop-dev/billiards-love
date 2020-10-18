package org.antop.billiardslove.jpa.entity;

import lombok.*;
import org.antop.billiardslove.jpa.convertor.BooleanConverter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TBL_NTC")
public class Notice {
    /**
     * 공지사항 아이디
     */
    @Id
    @Column(name = "NTC_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 제목
     */
    @Setter
    @Column(name = "NTC_TTL")
    private String title;
    /**
     * 내용
     */
    @Setter
    @Column(name = "NTC_CNTN")
    private String contents;
    /**
     * 대상 대회 아이디
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRGT_CNTS_ID")
    private Contest contest;
    /**
     * 스킵 가능 여부
     */
    @Setter
    @Convert(converter = BooleanConverter.class)
    @Column(name = "CAN_SKIP")
    private boolean canSkip;
    /**
     * 등록 관리자 아이디
     */
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
