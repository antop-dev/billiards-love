package org.antop.billiardslove.jpa.entity;

import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 관리자 계정
 *
 * @author jammini
 */
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TBL_MNGR")
public class Manager {
    /**
     * 관리자 아이디
     */
    @Id
    @Column(name = "MNGR_ID")
    @EqualsAndHashCode.Exclude
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
