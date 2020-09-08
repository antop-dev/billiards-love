package org.antop.billiardslove.jpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@Entity
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private KakaoLogin kakaoLogin;

    @Setter
    @Column(name = "LGN_TKN")
    private String loginToken;
}
