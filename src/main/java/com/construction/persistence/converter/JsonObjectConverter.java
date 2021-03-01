package com.construction.persistence.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.Map;

import static com.construction.appconfiguration.ApplicationConfiguration.OBJECT_MAPPER;

@Converter(autoApply = true)
public class JsonObjectConverter implements AttributeConverter<Map<String, Object>, String> {

    @Override
    public String convertToDatabaseColumn(final Map<String, Object> value) {
        try {
            return value == null ? null : OBJECT_MAPPER.writeValueAsString(value);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(final String value) {
        try {
            final var type = new TypeReference<Map<String, Object>>() {
            };
            return value == null ? null : OBJECT_MAPPER.readValue(value, type);
        } catch (final IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
