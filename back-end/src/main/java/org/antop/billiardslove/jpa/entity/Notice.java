package org.antop.billiardslove.jpa.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.antop.billiardslove.jpa.convertor.BooleanConverter;
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
import java.time.LocalDateTime;

/**
 * 공지사항
 *
 * @author jammini
 */
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TBL_NTC")
public class Notice {
    /**
     * 공지사항 아이디
     */
    @Id
    @Column(name = "NTC_ID")
    @EqualsAndHashCode.Include
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
    @ToString.Exclude
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
    public Notice(String title, String contents, Contest contest, boolean canSkip, Manager registrationUser, Manager modifyUser) {
        this.title = title;
        this.contents = contents;
        this.contest = contest;
        this.canSkip = canSkip;
        this.registrationUser = registrationUser;
        this.modifyUser = modifyUser;
    }
}
