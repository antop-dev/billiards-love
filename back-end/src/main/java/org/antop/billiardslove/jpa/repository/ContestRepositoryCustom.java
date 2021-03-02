package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.entity.Contest;

import java.util.List;
import java.util.Optional;

public interface ContestRepositoryCustom {

    Optional<Contest> findByIdWithFetch(long contestId);

    /**
     * 정렬하여 조회한다.
     *
     * @return 대회 목록
     */
    List<Contest> findAllOrdered();

}
