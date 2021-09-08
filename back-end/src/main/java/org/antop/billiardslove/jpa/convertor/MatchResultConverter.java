package org.antop.billiardslove.jpa.convertor;

import org.antop.billiardslove.model.MatchResult;
import org.antop.billiardslove.model.Outcome;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * CHAR(3) 에 들어있는 값을 {@link MatchResult}로 변환한다.
 * "WWW" ↔ { WIN, WIN, WIN }<br>
 * "LL " ↔ { LOSE, LOSE, NONE }<br>
 * "   " ↔ { NONE, NONE, NONE }
 *
 * @author antop
 */
@Converter
public class MatchResultConverter implements AttributeConverter<MatchResult, String> {
    @Override
    public String convertToDatabaseColumn(MatchResult attribute) {
        return Arrays.stream(attribute.toArray())
                .map(Outcome::getCode)
                .collect(Collectors.joining());
    }

    @Override
    public MatchResult convertToEntityAttribute(String dbData) {
        String[] split = dbData.split("");

        Outcome first = Outcome.of(split[0]);
        Outcome second = Outcome.of(split[1]);
        Outcome third = Outcome.of(split[2]);

        return MatchResult.of(first, second, third);
    }
}
