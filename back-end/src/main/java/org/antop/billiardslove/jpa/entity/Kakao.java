package org.antop.billiardslove.jpa.entity;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "TBL_KKO_LGN")
public class Kakao {

    @Id
    @Column(name = "LGN_ID")
    @GeneratedValue
    private Long id;

    @Column(name = "ACES_TKN", length = 100)
    private String accessToken;

    @Column(name = "NICK_NM")
    private String nickname;

    @Column(name = "PRFL_IMG_URL")
    private String profileImgUrl;

    @Column(name = "PRFL_THMB_IMG_URL")
    private String profileThumbUrl;

    @Column(name = "LST_CNCT_DT", nullable = false)
    private LocalTime lastConnectDay;

}
