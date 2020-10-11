package org.antop.billiardslove.jpa.entity;

import lombok.*;
import org.antop.billiardslove.jpa.domain.KakaoProfile;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 카카오 사용자 정보<br>
 * https://developers.kakao.com/docs/latest/ko/user-mgmt/common#user-info
 *
 * @author
 */
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TBL_KKO_LGN")
public class KakaoLogin {
    /**
     * 카카오톡 아이디
     */
    @Id
    @Column(name = "LGN_ID")
    private Long id;
    /**
     * 접속 일시
     */
    @Column(name = "LST_CNCT_DT")
    private LocalDateTime connectedAt;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "nickname", column = @Column(name = "NCK_NM")),
            @AttributeOverride(name = "imgUrl", column = @Column(name = "PRFL_IMG_URL")),
            @AttributeOverride(name = "thumbUrl", column = @Column(name = "PRFL_THMB_IMG_URL"))
    })
    private KakaoProfile profile;

    /**
     * 프로필을 변경한다.
     *
     * @param profile 변경할 새로운 프로필
     */
    public void changeProfile(KakaoProfile profile) {
        this.profile = profile;
    }

    /**
     * 접속함
     *
     * @param dateTime 접속 일시
     */
    public void connect(LocalDateTime dateTime) {
        this.connectedAt = dateTime;
    }
}
