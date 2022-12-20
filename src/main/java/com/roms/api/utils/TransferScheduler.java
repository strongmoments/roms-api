package com.roms.api.utils;

import com.roms.api.controller.LeaveRequestController;
import com.roms.api.model.EmploymentRecommendation;
import com.roms.api.service.ClientProjectSubteamMemberService;
import com.roms.api.service.EmploymentRecommendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;


@Component
@EnableScheduling
public class TransferScheduler {

    public static final Logger logger = LoggerFactory.getLogger(TransferScheduler.class);
    @Autowired
    private ClientProjectSubteamMemberService clientProjectSubteamMemberService;

    @Autowired
    private EmploymentRecommendService employmentRecommendService;

    @Scheduled(cron = "0 0/03 1 * * *") // every day at 1:03 am
    public void cronJobSch() {
        logger.info("employe transfer job started");
        List<EmploymentRecommendation> allRecommendation = employmentRecommendService.findAllPendingJobs();
        if(!allRecommendation.isEmpty()){
            allRecommendation.forEach(obj->{
                Instant perposedDate =obj.getDemandIdx().getPerposedDate();
                if(Instant.now().isAfter(perposedDate) || Instant.now().atOffset(ZoneOffset.UTC).toLocalDate().equals(perposedDate.atOffset(ZoneOffset.UTC).toLocalDate())){
                    clientProjectSubteamMemberService.transferToGang(obj.getId());
                }
            });
        }
        logger.info("employe transfer job done");
    }
}
