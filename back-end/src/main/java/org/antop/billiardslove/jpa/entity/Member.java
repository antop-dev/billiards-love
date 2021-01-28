package org.antop.billiardslove.jpa.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
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
@Table(name = "TBL_MMBR")
public class Member {
    /**
     * 회원 아이디
     */
    @Id
    @Column(name = "MMBR_ID")
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 별명
     */
    @Setter
    @Column(name = "MMBR_NCK_NM")
    private String nickname;
    /**
     * 핸디캡
     */
    @Setter
    @Column(name = "MMBR_HNDC")
    private Integer handicap;
    /**
     * 등록 일시
     */
    @CreatedDate
    @Column(name = "RGST_DT")
    private LocalDateTime registerDateTime;
    /**
     * 수정 일시
     */
    @LastModifiedDate
    @Column(name = "MDFY_DT")
    private LocalDateTime modifyDateTime;
    /**
     * 카카오 로그인 아이디
     */
    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KKO_LGN_ID")
    private KakaoLogin kakaoLogin;

    @Builder
    public Member(KakaoLogin kakaoLogin, String nickname, Integer handicap) {
        this.kakaoLogin = kakaoLogin;
        this.nickname = nickname;
        this.handicap = handicap;
    }
}
