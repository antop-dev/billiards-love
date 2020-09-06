package org.antop.billiardslove.jpa.convertor;

import org.antop.billiardslove.jpa.entity.ProgressStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ProgressStatusConverter implements AttributeConverter<ProgressStatus, String> {

    @Override
    public String convertToDatabaseColumn(ProgressStatus attribute) {
        return attribute.getStatus();
    }

    @Override
    public ProgressStatus convertToEntityAttribute(String dbData) {
        return ProgressStatus.of(dbData);
    }
}
