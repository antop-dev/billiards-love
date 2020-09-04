package org.antop.billiardslove.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "TBL_MNGR")
public class Manager {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue
    private Long id;

    @Column(name = "LGN_ID")
    private String username;

    @Column(name = "LGN_BOX")
    private String password;

    @Column(name = "LAST_LGN_DT")
    private LocalDateTime lastLoginDateTime;
}
