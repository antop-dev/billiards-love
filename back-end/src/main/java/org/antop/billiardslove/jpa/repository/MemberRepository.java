package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.jpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
