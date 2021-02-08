package org.antop.billiardslove.jpa.convertor;

import org.antop.billiardslove.jpa.entity.Match;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * CHAR(3) 에 들어있는 값을 {@link Match.MatchResult}로 변환한다.
 * "WWW" ↔ { WIN, WIN, WIN }<br>
 * "LL " ↔ { LOSE, LOSE, NONE }<br>
 * "   " ↔ { NONE, NONE, NONE }
 *
 * @author antop
 */
public class MatchResultConverter implements AttributeConverter<Match.MatchResult, String> {
    @Override
    public String convertToDatabaseColumn(Match.MatchResult attribute) {
        return Arrays.stream(attribute.toArray())
                .map(Match.Result::getCode)
                .collect(Collectors.joining());
    }

    @Override
    public Match.MatchResult convertToEntityAttribute(String dbData) {
        String[] split = dbData.split("");

        Match.Result first = Match.Result.of(split[0]);
        Match.Result second = Match.Result.of(split[1]);
        Match.Result third = Match.Result.of(split[2]);

        return Match.MatchResult.of(first, second, third);
    }
}
