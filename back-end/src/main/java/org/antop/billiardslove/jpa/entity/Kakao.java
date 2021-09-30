package org.antop.billiardslove.jpa.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 카카오 사용자 정보<br>
 * https://developers.kakao.com/docs/latest/ko/user-mgmt/common#user-info
 *
 * @author jammini
 */
@Getter
@ToString
@FieldNameConstants
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
    private Long id;
    /**
     * 접속 일시
     */
    @NotNull
    @Setter
    @Column(name = "lst_cnct_dt")
    private LocalDateTime connectedAt;

    @Setter
    @Embedded
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
    @FieldNameConstants
    public static class Profile {
        /**
         * 닉네임
         */
        @Column(name = "nck_nm")
        private String nickname;
        /**
         * 프로필 이미지 URL, 640px * 640px 또는 480px * 480px
         */
        @Column(name = "prfl_img_url")
        private String imgUrl;
        /**
         * 프로필 미리보기 이미지 URL, 110px * 110px 또는 100px * 100px
         */
        @Column(name = "prfl_thmb_img_url")
        private String thumbUrl;
    }
}
