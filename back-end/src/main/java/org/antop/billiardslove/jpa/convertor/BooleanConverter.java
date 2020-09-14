package org.antop.billiardslove.jpa.convertor;

import org.antop.billiardslove.jpa.JpaConstants;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static org.antop.billiardslove.jpa.JpaConstants.TRUE;

@Converter
public class BooleanConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        if (attribute == null) return JpaConstants.FALSE;
        return attribute ? TRUE : JpaConstants.FALSE;
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return TRUE.equals(dbData);
    }
}
