package org.antop.billiardslove.dao;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.constants.CodeGroups;
import org.antop.billiardslove.jpa.entity.Code;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresent;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

class CodeDaoTest extends SpringBootBase {
    @Autowired
    private CodeDao dao;

    @Test
    void findByGroup() {
        List<Code> codes = dao.findByGroup(CodeGroups.CONTEST_STATE);
        assertThat(codes, hasSize(5));
    }

    @Test
    void findById() {
        Optional<Code> optional = dao.findById(CodeGroups.CONTEST_STATE, "1");
        assertThat(optional, isPresent());
        optional.ifPresent(it -> assertThat(it.getName(), is("접수중")));
    }
}
