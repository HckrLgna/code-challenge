package com.lgnasolutions.backend_challenge.infraestructure.mappers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

import java.util.Map;

public class JsonMapConverter implements AttributeConverter<Map<String, Object>,String> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> stringObjectMap) {
        try{
            return mapper.writeValueAsString(stringObjectMap);
        }catch(Exception ex){
            throw new IllegalArgumentException("Error converting Map to JSON string", ex);
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String s) {
        try{
            return mapper.readValue(s, new TypeReference<Map<String, Object>>() {});
        }catch(Exception ex){
            throw new IllegalArgumentException("Error converting JSON string to Map", ex);
        }
    }
}
