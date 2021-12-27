package org.antop.billiardslove.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class OutcomeTest {

    @Test
    void scoreFunc() {
        assertThat(Outcome.WIN.getScoreFunc().apply(2), is(5));
        assertThat(Outcome.LOSE.getScoreFunc().apply(2), is(3));
        assertThat(Outcome.HOLD.getScoreFunc().apply(2), is(1));
        assertThat(Outcome.ABSTENTION.getScoreFunc().apply(3), is(0));
        assertThat(Outcome.NONE.getScoreFunc().apply(99), is(99));
    }
}
