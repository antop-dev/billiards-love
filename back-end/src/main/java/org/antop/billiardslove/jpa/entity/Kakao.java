package org.antop.billiardslove.jpa.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 카카오 사용자 정보<br>
 * https://developers.kakao.com/docs/latest/ko/user-mgmt/common#user-info
 *
 * @author jammini
 */
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_kko_lgn")
public class Kakao {
    /**
     * 카카오 아이디
     */
    @Id
    @Column(name = "lgn_id")
    @EqualsAndHashCode.Include
    private Long id;
    /**
     * 접속 일시
     */
    @Setter
    @Column(name = "lst_cnct_dt")
    private LocalDateTime connectedAt;

    @Setter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "nickname", column = @Column(name = "nck_nm")),
            @AttributeOverride(name = "imgUrl", column = @Column(name = "prfl_img_url")),
            @AttributeOverride(name = "thumbUrl", column = @Column(name = "prfl_thmb_img_url"))
    })
    private Profile profile;

    /**
     * 프로필을 변경한다.
     *
     * @param profile 변경할 새로운 프로필
     */
    public void changeProfile(Profile profile) {
        this.profile = profile;
    }

    /**
     * 카카오 프로파일
     *
     * @author antop
     */
    @Embeddable
    @Getter
    @ToString
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static class Profile {
        /**
         * 닉네임
         */
        private String nickname;
        /**
         * 프로필 이미지 URL, 640px * 640px 또는 480px * 480px
         */
        private String imgUrl;
        /**
         * 프로필 미리보기 이미지 URL, 110px * 110px 또는 100px * 100px
         */
        private String thumbUrl;
    }
}
