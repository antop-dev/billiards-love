package org.antop.billiardslove.model;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.antop.billiardslove.model.Outcome.NONE;
import static org.antop.billiardslove.model.Outcome.WIN;

class MatchResultTest {

    @Test
    void toArray() {
        MatchResult result = MatchResult.of(WIN, WIN, WIN);
        MatcherAssert.assertThat(result.toArray(), Matchers.is(new Outcome[]{WIN, WIN, WIN}));
    }

    @Test
    void none() {
        MatchResult result = MatchResult.NONE;
        MatcherAssert.assertThat(result.toArray(), Matchers.is(new Outcome[]{NONE, NONE, NONE}));
    }

}
