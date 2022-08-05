package com.roms.api.kafka;

import com.roms.api.model.Users;
import com.roms.api.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;

@Component
@Transactional
public class KafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);


   @Autowired
   private UserService userService;
    @KafkaListener(topics = "4igc0qsg-rtl.kafka.data.save", groupId = "rtl")
    public void processPostBrand(String userJSON){
        logger.info("received content = '{}'", userJSON);
        try{
            ObjectMapper mapper = new ObjectMapper();
            Users userModel = mapper.readValue(userJSON, Users.class);
            Users brand = userService.save(userModel);
            logger.info("Success process brand '{}' with topic '{}'", userModel.getId(), "rtl.kafka.data.save");
        } catch (Exception e){
            logger.error("An error occurred! '{}'", e.getMessage());
        }
    }

}