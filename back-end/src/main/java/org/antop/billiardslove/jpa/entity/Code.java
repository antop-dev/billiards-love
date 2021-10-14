package org.antop.billiardslove.jpa.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 코드
 *
 * @author antop
 */
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@FieldNameConstants
@Builder
@Entity
@Table(name = "tbl_cd")
@IdClass(CodeId.class)
public class Code {
    /**
     * 코드 그룹
     */
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cd_grp_id")
    @ToString.Exclude
    private CodeGroup group;

    /**
     * 코드 아이디
     */
    @Id
    @Column(name = "cd_id")
    private String id;

    /**
     * 코드명
     */
    @Column(name = "cd_nm")
    private String name;

    /**
     * 정렬 순서
     */
    @Column(name = "ord_no")
    private int order;

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

}
