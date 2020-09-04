package org.antop.billiardslove.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "TBL_MMBR")
public class Member {

    @Id
    @Column(name = "MMBR_ID")
    @GeneratedValue
    private Long id;

    @Column(name = "MMBR_NCK_NM")
    private String nickname;

    @Column(name = "MMBR_HNDC")
    private int handicap;

    @Column(name = "RGST_DT")
    private LocalDateTime registerDateTime;

    @Column(name = "MDFY_DT")
    private LocalDateTime modifyDateTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private KakaoLogin kakaoLogin;

    @Column(name = "LGN_TKN")
    private String loginToken;
}
