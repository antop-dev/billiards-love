package org.antop.billiardslove.jpa.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 관리자 계정
 *
 * @author jammini
 */
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TBL_MNGR")
public class Manager {
    /**
     * 관리자 아이디
     */
    @Id
    @Column(name = "MNGR_ID")
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 로그인 아이디
     */
    @Setter
    @Column(name = "LGN_ID")
    private String username;
    /**
     * 로그인 비밀번호
     */
    @Setter
    @Column(name = "LGN_BOX")
    private String password;
    /**
     * 마지막 로그인 일시
     */
    @LastModifiedDate
    @Column(name = "LAST_LGN_DT")
    private LocalDateTime lastLoginDateTime;

    @Builder
    public Manager(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
