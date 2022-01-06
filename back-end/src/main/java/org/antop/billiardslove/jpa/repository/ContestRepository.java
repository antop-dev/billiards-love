package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Optional;

public interface ContestRepository extends JpaRepository<Contest, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(value = @QueryHint(name = "javax.persistence.lock.timeout", value = "3000"))
    @Query("select c from Contest c where c.id = :contestId")
    Optional<Contest> findByIdForUpdate(long contestId);

}
