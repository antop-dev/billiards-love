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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 회원 정보
 *
 * @author jammini
 */
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tbl_mmbr")
public class Member {
    /**
     * 회원 아이디
     */
    @Id
    @Column(name = "mmbr_id")
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 별명
     */
    @Setter
    @Column(name = "mmbr_nck_nm")
    private String nickname;
    /**
     * 핸디캡
     */
    @Setter
    @Column(name = "mmbr_hndc")
    private Integer handicap;
    /**
     * 등록 일시
     */
    @CreatedDate
    @Column(name = "rgst_dt")
    private LocalDateTime created;
    /**
     * 수정 일시
     */
    @LastModifiedDate
    @Column(name = "mdfy_dt")
    private LocalDateTime modified;

    @Convert(converter = BooleanConverter.class)
    @Column(name = "mgnr_yn")
    private boolean manager;

    /**
     * 카카오 로그인 아이디
     */
    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "kko_lgn_id")
    private Kakao kakao;

    @Builder
    public Member(Kakao kakao, String nickname) {
        this.kakao = kakao;
        this.nickname = nickname;
    }

}
