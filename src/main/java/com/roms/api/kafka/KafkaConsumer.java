package com.roms.api.kafka;

import com.roms.api.model.Employe;
import com.roms.api.model.Users;
import com.roms.api.service.EmployeService;
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

    @Autowired
    private EmployeService employeService;

    @KafkaListener(topics = "user-rtl.kafka.data.save", groupId = "rtl")
    public void processUser(String userJSON){
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

    @KafkaListener(topics = "employee-rtl.kafka.data.save", groupId = "rtl")
    public void processEmployee(String userJSON){
        logger.info("received content = '{}'", userJSON);
        try{
            ObjectMapper mapper = new ObjectMapper();
            Employe employee = mapper.readValue(userJSON, Employe.class);
            Employe brand = employeService.save(employee);
            logger.info("Success process brand '{}' with topic '{}'", employee.getId(), "rtl.kafka.data.save");
        } catch (Exception e){
            logger.error("An error occurred! '{}'", e.getMessage());
        }
    }

}