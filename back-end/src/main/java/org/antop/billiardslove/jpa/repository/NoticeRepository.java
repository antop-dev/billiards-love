package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
