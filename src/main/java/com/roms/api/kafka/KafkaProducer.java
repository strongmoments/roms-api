package com.roms.api.kafka;

import com.roms.api.model.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplates;

    public void postBrand(String topic, String groupId, Users userModel){
        try {
            logger.info("Sending data to kafka = '{}' with topic '{}'", userModel.getId(), topic);
            ObjectMapper mapper = new ObjectMapper();
            kafkaTemplates.send(topic, groupId, mapper.writeValueAsString(userModel));
        } catch (Exception e) {
            logger.error("An error occurred! '{}'", e.getMessage());
        }
    }

}