package org.antop.billiardslove.jpa.convertor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanConverter implements AttributeConverter<Boolean, String> {
    public static final String TRUE = "Y";
    public static final String FALSE = "N";

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return (attribute == null || !attribute) ? FALSE : TRUE;
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return TRUE.equals(dbData);
    }
}
