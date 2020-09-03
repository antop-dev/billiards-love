package org.antop.billiardslove.jpa.convertor;

import org.antop.billiardslove.jpa.entity.WinLoseStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class WinLoseStatuseWLConverter implements AttributeConverter<WinLoseStatus, Character> {
    @Override
    public Character convertToDatabaseColumn(WinLoseStatus attribute) {
        if (attribute.equals(WinLoseStatus.WINNER)) {
            return '승';
        } else if (attribute.equals(WinLoseStatus.LOSE)) {
            return '패';
        } else {
            return '무';
        }
    }

    @Override
    public WinLoseStatus convertToEntityAttribute(Character dbData) {
        if (dbData.equals('승')) {
            return WinLoseStatus.WINNER;
        } else if (dbData.equals('패')) {
            return WinLoseStatus.LOSE;
        } else {
            return WinLoseStatus.HOLD;
        }
    }
}
