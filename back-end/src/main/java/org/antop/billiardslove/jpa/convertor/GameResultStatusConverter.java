package org.antop.billiardslove.jpa.convertor;

import org.antop.billiardslove.jpa.entity.GameResultStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class GameResultStatusConverter implements AttributeConverter<GameResultStatus, String> {
    @Override
    public String convertToDatabaseColumn(GameResultStatus attribute) {
        if (attribute.equals(GameResultStatus.WINNER)) {
            return "승리";
        } else if (attribute.equals(GameResultStatus.LOSE)) {
            return "패배";
        } else if (attribute.equals(GameResultStatus.ABSTENTION)) {
            return "기권";
        } else if (attribute.equals(GameResultStatus.HOLD)) {
            return "보류";
        } else {
            return "NONE";
        }
    }

    @Override
    public GameResultStatus convertToEntityAttribute(String dbData) {
        if ("승리".equals(dbData)) {
            return GameResultStatus.WINNER;
        } else if ("패배".equals(dbData)) {
            return GameResultStatus.LOSE;
        } else if ("기권".equals(dbData)) {
            return GameResultStatus.ABSTENTION;
        } else if ("보류".equals(dbData)) {
            return GameResultStatus.HOLD;
        } else {
            return GameResultStatus.NONE;
        }
    }
}
