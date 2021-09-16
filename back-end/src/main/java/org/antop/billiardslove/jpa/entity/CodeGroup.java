package org.antop.billiardslove.jpa.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 코드 그룹
 *
 * @author antop
 */
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldNameConstants
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tbl_cd_grp")
public class CodeGroup {

    @NotNull
    @Id
    @Column(name = "cd_grp_id", length = 10)
    private String id;

    @NotNull
    @Column(name = "cd_grp_nm", length = 50)
    private String name;

    @NotNull
    @Column(name = "cd_grp_dscr")
    private String description;

    /**
     * 등록 일시
     */
    @CreatedDate
    @Column(name = "rgst_dt")
    private LocalDateTime created;
    /**
     * 마지막 수정 일시
     */
    @LastModifiedDate
    @Column(name = "mdfy_dt")
    private LocalDateTime modified;

    @Builder
    protected CodeGroup(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

}
