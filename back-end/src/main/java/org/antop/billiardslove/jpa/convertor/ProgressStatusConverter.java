package org.antop.billiardslove.jpa.convertor;

import org.antop.billiardslove.core.ProgressStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ProgressStatusConverter implements AttributeConverter<ProgressStatus, String> {

    @Override
    public String convertToDatabaseColumn(ProgressStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public ProgressStatus convertToEntityAttribute(String dbData) {
        return ProgressStatus.of(dbData);
    }
}
