package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContestRepository extends JpaRepository<Contest, Long> {
    List<Contest> findAllByOrderByStateAsc();
}
