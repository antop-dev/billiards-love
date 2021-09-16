package org.antop.billiardslove.dao;

import lombok.extern.slf4j.Slf4j;
import org.antop.billiardslove.jpa.entity.Code;
import org.antop.billiardslove.jpa.repository.CodeRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.antop.billiardslove.jpa.entity.QCode.code;

@Slf4j
@Repository
@Transactional(readOnly = true)
public class CodeDao extends QuerydslRepositorySupport {
    private final CodeRepository repository;

    public CodeDao(CodeRepository repository) {
        super(Code.class);
        this.repository = repository;
    }

    public List<Code> findByGroup(String group) {
        return from(code).where(code.group.id.eq(group)).fetch();
    }

    public Optional<Code> findById(String group, String id) {
        return Optional.ofNullable(from(code).where(code.group.id.eq(group).and(code.id.eq(id))).fetchOne());
    }

}
