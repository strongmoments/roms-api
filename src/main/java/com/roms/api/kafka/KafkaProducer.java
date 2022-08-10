package com.roms.api.kafka;

import com.google.gson.*;
import com.roms.api.model.Employe;
import com.roms.api.model.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class KafkaProducer {


    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplates;

    public void postUser(String topic, String groupId, Users userModel){
        try {
            logger.info("Sending data to kafka = '{}' with topic '{}'", userModel.getId(), topic);
            Gson mappers =   new GsonBuilder().registerTypeAdapter(Instant.class, new JsonSerializer<Instant>() {
                @Override
                public JsonElement serialize(Instant src, Type srcType, JsonSerializationContext context) {
                    return new JsonPrimitive(src.toString());
                }
            }).create();
            kafkaTemplates.send(topic, groupId, mappers.toJson(userModel));
            //kafkaTemplates.sent
        } catch (Exception e) {
            logger.error("An error occurred! '{}'", e.getMessage());
        }
    }


}