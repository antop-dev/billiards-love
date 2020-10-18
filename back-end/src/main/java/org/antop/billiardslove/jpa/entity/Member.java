package org.antop.billiardslove.jpa.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
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

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TBL_MMBR")
public class Member {
    /**
     * 회원아이디
     */
    @Id
    @Column(name = "MMBR_ID")
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
     * 등록일시
     */
    @CreatedDate
    @Column(name = "RGST_DT")
    private LocalDateTime registerDateTime;
    /**
     * 수정일시
     */
    @LastModifiedDate
    @Column(name = "MDFY_DT")
    private LocalDateTime modifyDateTime;
    /**
     * 카카오 로그인 아이디
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KKO_LGN_ID")
    private KakaoLogin kakaoLogin;
    /**
     * 로그인 토큰
     */
    @Setter
    @Column(name = "LGN_TKN")
    private String loginToken;

}
