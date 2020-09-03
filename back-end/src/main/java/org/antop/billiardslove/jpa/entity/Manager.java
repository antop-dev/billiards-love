package org.antop.billiardslove.jpa.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_MNGR")
public class Manager {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue
    private Long id;

    @Column(name = "LGN_ID", nullable = false, length = 100)
    private String username;

    @Column(name = "LGN_BOX", nullable = false)
    private String password;

    @Column(name = "LAST_LGN_DT")
    private LocalDateTime lastLoginDate;
}
