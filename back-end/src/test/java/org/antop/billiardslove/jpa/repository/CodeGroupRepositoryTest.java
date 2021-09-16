package org.antop.billiardslove.jpa.repository;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.jpa.entity.CodeGroup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class CodeGroupRepositoryTest extends SpringBootBase {
    @Autowired
    private CodeGroupRepository repository;

    @Test
    void save() {
        CodeGroup group = CodeGroup.builder()
                .id("010101")
                .name("코드 그룹 생성")
                .description("코드 그룹을 생성한다.")
                .build();

        CodeGroup saved = repository.save(group);
        assertThat(isPersisted(group), is(false));
        assertThat(isPersisted(saved), is(true));
        assertNotSame(group, saved);
    }
}
