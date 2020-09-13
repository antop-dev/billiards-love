package org.antop.billiardslove.jpa.convertor;

import org.antop.billiardslove.jpa.entity.GameResultStatus;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameResultStatusConverterTest {
    private final GameResultStatusConverter converter = new GameResultStatusConverter();

    @Test
    void convertToDatabaseColumn() {
        assertThat(converter.convertToDatabaseColumn(GameResultStatus.NONE), is("0"));
        assertThat(converter.convertToDatabaseColumn(GameResultStatus.WIN), is("1"));
        assertThat(converter.convertToDatabaseColumn(GameResultStatus.LOSE), is("2"));
        assertThat(converter.convertToDatabaseColumn(GameResultStatus.ABSTENTION), is("3"));
        assertThat(converter.convertToDatabaseColumn(GameResultStatus.HOLD), is("4"));
        assertThat(converter.convertToDatabaseColumn(null), nullValue());
    }

    @Test
    void convertToEntityAttribute() {
        assertThat(converter.convertToEntityAttribute("0"), is(GameResultStatus.NONE));
        assertThat(converter.convertToEntityAttribute("1"), is(GameResultStatus.WIN));
        assertThat(converter.convertToEntityAttribute("2"), is(GameResultStatus.LOSE));
        assertThat(converter.convertToEntityAttribute("3"), is(GameResultStatus.ABSTENTION));
        assertThat(converter.convertToEntityAttribute("4"), is(GameResultStatus.HOLD));
        assertThrows(IllegalArgumentException.class, () -> converter.convertToEntityAttribute(null));
        assertThrows(IllegalArgumentException.class, () -> converter.convertToEntityAttribute("7"));
    }
}
