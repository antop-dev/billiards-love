package org.antop.billiardslove.jpa.entity;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CodeIdTest {

    @Test
    void mustTrueEquals() {
        CodeId codeId1 = CodeId.of("1", "1");
        CodeId codeId2 = CodeId.of("1", "1");
        assertEquals(codeId1, codeId2);
    }

    @Test
    void mustSameHashCode() {
        CodeId codeId1 = CodeId.of("2", "1");
        CodeId codeId2 = CodeId.of("2", "1");
        assertThat(codeId1.hashCode(), is(codeId2.hashCode()));
    }

}
