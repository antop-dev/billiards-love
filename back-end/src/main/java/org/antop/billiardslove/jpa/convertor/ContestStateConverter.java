package org.antop.billiardslove.jpa.convertor;

import org.antop.billiardslove.model.ContestState;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ContestStateConverter implements AttributeConverter<ContestState, String> {

    @Override
    public String convertToDatabaseColumn(ContestState attribute) {
        return attribute.getCode();
    }

    @Override
    public ContestState convertToEntityAttribute(String dbData) {
        return ContestState.of(dbData);
    }
}
