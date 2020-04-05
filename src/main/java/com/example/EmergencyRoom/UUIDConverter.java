package com.example.EmergencyRoom;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.UUID;

//This class handles conversions to ensure everything works well with postgresql using UUID type IDs

@Converter(autoApply = true)
public class UUIDConverter implements AttributeConverter<UUID, UUID> {
    @Override
    public UUID convertToDatabaseColumn(UUID attribute) {
        return attribute;
    }
    @Override
    public UUID convertToEntityAttribute(UUID dbData) {
        return dbData;
    }
}