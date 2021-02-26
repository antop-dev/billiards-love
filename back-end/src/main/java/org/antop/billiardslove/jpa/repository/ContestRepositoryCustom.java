package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.entity.Contest;

import java.util.List;

public interface ContestRepositoryCustom {

    /**
     * 정렬하여 조회한다.
     *
     * @return 대회 목록
     */
    List<Contest> findAllOrdered();

}
