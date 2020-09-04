package org.antop.billiardslove.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "TBL_KKO_LGN")
public class KakaoLogin {

    @Id
    @Column(name = "LGN_ID")
    @GeneratedValue
    private Long id;

    @Column(name = "ACES_TKN")
    private String accessToken;

    @Column(name = "NICK_NM")
    private String nickname;

    @Embedded
    private Profile profile;

    @Column(name = "LST_CNCT_DT")
    private LocalDateTime lastConnectDateTime;

    @Embeddable
    public class Profile{
        @Column(name = "PRFL_IMG_URL")
        private String imgUrl;

        @Column(name = "PRFL_THMB_IMG_URL")
        private String thumbUrl;
    }
}
