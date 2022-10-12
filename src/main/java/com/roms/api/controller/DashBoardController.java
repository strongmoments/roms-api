package com.roms.api.controller;

import com.roms.api.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/dashboard")
public class DashBoardController {

    @Autowired
    private EmployeService employeService;
    @GetMapping(value = "/load")
    public ResponseEntity<?> searchProjectSubTeam() throws ChangeSetPersister.NotFoundException {
        Map<String, Object> response = new HashMap<>();
        Long totalEmployee = employeService.getTotalEmployeeCount();
        totalEmployee = totalEmployee-1; // to remove admin user
        List<Instant>  dobList = employeService.findDobOfEmployees();
        Instant toDate = Instant.now();
        Instant fromDate = toDate.minus(30, ChronoUnit.DAYS);

        AtomicInteger counter = new AtomicInteger();
        AtomicReference<Integer> countwithoutNull = new AtomicReference<>(0);
        dobList.forEach(date ->{
            if(date != null){
                countwithoutNull.getAndSet(countwithoutNull.get() + 1);
                Period age = Period.between(LocalDate.ofInstant(date, ZoneId.of("UTC+10:00")), LocalDate.ofInstant(Instant.now(),ZoneId.of("UTC+10:00")));
                counter.addAndGet(age.getYears());
            }

        });
        response.put("employeeCount",totalEmployee);
        if(totalEmployee == 0){
            totalEmployee =1L;
        }
        response.put("employeeDobAverage",counter.get()/countwithoutNull.get());
        response.put("starters",employeService.getNewEmployeeCountBetween(fromDate,toDate));


        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
