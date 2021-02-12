package org.antop.billiardslove.jpa.convertor;

import org.antop.billiardslove.jpa.entity.Contest;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ContestStateConverter implements AttributeConverter<Contest.State, String> {

    @Override
    public String convertToDatabaseColumn(Contest.State attribute) {
        return attribute.getCode();
    }

    @Override
    public Contest.State convertToEntityAttribute(String dbData) {
        return Contest.State.of(dbData);
    }
}
