package com.roms.api.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.Map;

public class ModelHashMapConverter implements AttributeConverter <Map<String, Object>, String> {

    protected final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(Map<String, Object> data) {
        String dataStringJson = null;

        try {
            dataStringJson = objectMapper.writeValueAsString(data);
        } catch (final JsonProcessingException e) {

            logger.error("JSON writing error", e);
        }

        return dataStringJson;
    }

    @Override
    public  Map<String, Object>  convertToEntityAttribute(String dataStringJson) {
        Map<String, Object> data = null;
        try {
            data = objectMapper.readValue(dataStringJson, Map.class);
        } catch (final IOException e) {
            logger.error("JSON reading error", e);
        }

        return data;
    }
}
