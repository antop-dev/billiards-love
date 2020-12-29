package org.antop.billiardslove.jpa.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 앱(웹) 초기화
 *
 * @author jammini
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Init {
    /**
     * 장비 아이디
     */
    @Id
    private String deviceId;
    /**
     * 서버에서 만들어준 앱 아이디
     */
    private String appId;
}
