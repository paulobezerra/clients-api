package io.platformbuilders.clients.infra.converters;

import io.platformbuilders.clients.infra.enums.PersonTypeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PersonTypeConverter implements AttributeConverter<PersonTypeEnum, String> {
    @Override
    public String convertToDatabaseColumn(PersonTypeEnum attribute) {
        return attribute.toString();
    }

    @Override
    public PersonTypeEnum convertToEntityAttribute(String dbData) {
        return PersonTypeEnum.valueOf(dbData.toUpperCase());
    }
}
