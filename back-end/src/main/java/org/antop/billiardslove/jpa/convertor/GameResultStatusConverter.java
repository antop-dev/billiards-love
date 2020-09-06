package org.antop.billiardslove.jpa.convertor;

import org.antop.billiardslove.jpa.entity.GameResultStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class GameResultStatusConverter implements AttributeConverter<GameResultStatus, String> {
    @Override
    public String convertToDatabaseColumn(GameResultStatus attribute) {
        return attribute.getStatus();
    }

    @Override
    public GameResultStatus convertToEntityAttribute(String dbData) {
        return GameResultStatus.of(dbData);
    }
}
