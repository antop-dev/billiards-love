package org.antop.billiardslove.jpa.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TBL_MMBR")
public class Member {

    @Id
    @Column(name = "MMBR_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "MMBR_NCK_NM")
    private String nickname;

    @Setter
    @Column(name = "MMBR_HNDC")
    private int handicap;

    @CreatedDate
    @Column(name = "RGST_DT")
    private LocalDateTime registerDateTime;

    @LastModifiedDate
    @Column(name = "MDFY_DT")
    private LocalDateTime modifyDateTime;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KKO_LGN_ID")
    private KakaoLogin kakaoLogin;

    @Setter
    @Column(name = "LGN_TKN")
    private String loginToken;
}
