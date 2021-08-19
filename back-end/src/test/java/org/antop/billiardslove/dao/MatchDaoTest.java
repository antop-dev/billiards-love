package org.antop.billiardslove.dao;

import org.antop.billiardslove.SpringBootBase;
import org.antop.billiardslove.jpa.entity.Match;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

class MatchDaoTest extends SpringBootBase {
    @Autowired
    private MatchDao dao;

    @Test
    void findInMe() {
        List<Match> matches = dao.findJoinedIn(1, 2);
        assertThat(matches, hasSize(4));
    }
}
