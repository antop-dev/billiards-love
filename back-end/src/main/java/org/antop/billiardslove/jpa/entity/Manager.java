package org.antop.billiardslove.jpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "TBL_MNGR")
public class Manager {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "LGN_ID")
    private String username;

    @Setter
    @Column(name = "LGN_BOX")
    private String password;

    @LastModifiedDate
    @Column(name = "LAST_LGN_DT")
    private LocalDateTime lastLoginDateTime;
}
