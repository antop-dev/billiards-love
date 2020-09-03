package org.antop.billiardslove.jpa.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_MMBR")
public class Member {

    @Id
    @Column(name = "MMBR_ID")
    @GeneratedValue
    private Long id;

    @Column(name = "MMBR_NCK_NM", length = 50)
    private String nickname;

    @Column(name = "MMBR_HNDC")
    private Byte handicap;

    @Column(name = "RGST_DT", nullable = false)
    private LocalDateTime registerDate;

    @Column(name = "MDFY_DT")
    private LocalDateTime modifyDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Kakao kakao;

    @Column(name = "LGN_TKN", nullable = false)
    private String loginToken;
}
