package org.antop.billiardslove.jpa.convertor;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class BooleanConverterTest {
    private final BooleanConverter converter = new BooleanConverter();

    @Test
    void convertToDatabaseColumn() {
        assertThat(converter.convertToDatabaseColumn(true), is("Y"));
        assertThat(converter.convertToDatabaseColumn(false), is("N"));
        assertThat(converter.convertToDatabaseColumn(null), is("N"));
    }

    @Test
    void convertToEntityAttribute() {
        assertThat(converter.convertToEntityAttribute("Y"), is(true));
        assertThat(converter.convertToEntityAttribute("y"), is(false));
        assertThat(converter.convertToEntityAttribute("N"), is(false));
        assertThat(converter.convertToEntityAttribute("n"), is(false));
        assertThat(converter.convertToEntityAttribute(null), is(false));
    }
}
