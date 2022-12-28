package com.roms.api.controller;

import com.roms.api.model.CheckInCheckOutHistory;
import com.roms.api.model.ClientProjectSubteam;
import com.roms.api.model.EmployeeAttendance;
import com.roms.api.model.LeaveRequest;
import com.roms.api.requestInput.CheckInCheckOutInput;
import com.roms.api.service.CheckInCheckOutHistoryService;
import com.roms.api.service.ClientProjectSubteamMemberService;
import com.roms.api.service.EmployeeAttendanceService;
import com.roms.api.utils.LoggedInUserDetails;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/employee/attendance")
public class EmployeeAttendanceController {

    @Autowired
    private EmployeeAttendanceService employeeAttendanceService;

    @Autowired
    private CheckInCheckOutHistoryService checkInCheckOutHistoryService;

    @Autowired
    private ClientProjectSubteamMemberService clientProjectSubteamMemberService;

    @Autowired
    private LoggedInUserDetails loggedIn;

    @PostMapping("")
    public ResponseEntity<?> syncWithMobile(@RequestBody()CheckInCheckOutInput request){
        Map<String,Object> response = new HashMap();
        try {
            SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            if(!request.getCheckInCheckout().isEmpty()) {
               /* String attendanceId = request.getCheckInCheckout().get(0).getEmployeeAttendance().getId();
                EmployeeAttendance employeeAttendance = new EmployeeAttendance();
                boolean isAttancdeIdIsValid= false;
                if(!StringUtils.isBlank(attendanceId)) {
                    Optional<EmployeeAttendance> attendance = employeeAttendanceService.findById(attendanceId);
                    if (attendance.isPresent()) {
                        employeeAttendance = attendance.get();
                        isAttancdeIdIsValid = true;
                    }
                }
                if(StringUtils.isBlank(attendanceId) && !isAttancdeIdIsValid){
                    Optional<ClientProjectSubteam>  gang= clientProjectSubteamMemberService.findEmployeeGangByEmployeId(loggedIn.getUser().getEmployeId().getId());

                    EmployeeAttendance employeeAttendanceModel = EmployeeAttendance.builder()
                            .totalHour(0L)
                            .employe(loggedIn.getUser().getEmployeId())
                            .build();
                    if(gang.isPresent()){
                        employeeAttendance.setGang(gang.get());
                    }
                    employeeAttendance = employeeAttendanceService.save(employeeAttendanceModel);
                }
*/

                List<CheckInCheckOutHistory> checkInHistory = new ArrayList<CheckInCheckOutHistory>();
               // EmployeeAttendance finalEmployeeAttendance = employeeAttendance;
                AtomicReference<Long> calculatedtotalHour = new AtomicReference<>(0l);
                request.getCheckInCheckout().forEach(obj -> {

                        obj.setOrganisation(loggedIn.getOrg());
                        try {
                            if(StringUtils.isNotBlank(obj.getCheckinSystemStr())){
                                Instant checkinSystem = dateTime.parse(obj.getCheckinSystemStr()).toInstant();
                                obj.setCheckinSystem(checkinSystem);
                            }
                            if(StringUtils.isNotBlank(obj.getCheckoutSystemStr())){
                                Instant checkoutSystek = dateTime.parse(obj.getCheckoutSystemStr()).toInstant();
                                obj.setCheckinSystem(checkoutSystek);
                            }

                            if(StringUtils.isNotBlank(obj.getCheckinStr())){
                                Instant checkin = dateTime.parse(obj.getCheckinStr()).toInstant();
                                obj.setCheckin(checkin);
                            }
                            if(StringUtils.isNotBlank(obj.getCheckoutStr())){
                                Instant checkOut = dateTime.parse(obj.getCheckoutStr()).toInstant();
                                obj.setCheckout(checkOut);
                            }


                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                       // calculatedtotalHour.set(calculatedtotalHour.get() + ChronoUnit.HOURS.between(obj.getCheckin(), obj.getCheckout()));
                    Long totalHour =0L;
                       if(obj.getCheckin() != null &&  obj.getCheckout() != null){
                            totalHour = ChronoUnit.HOURS.between(obj.getCheckin(), obj.getCheckout());
                       }
                        obj.setEmploye(loggedIn.getUser().getEmployeId());
                        obj.setCreateBy(loggedIn.getUser());
                        obj.setCreateDate(Instant.now());
                        obj.setTotalHour(totalHour);

                        checkInHistory.add(obj);

                });
                checkInCheckOutHistoryService.saveAll(checkInHistory);

               /* if(employeeAttendance.getCheckIn() == null){
                    employeeAttendance.setCheckIn(checkInHistory.get(0).getCheckin());
                    employeeAttendance.setCheckInAddress(checkInHistory.get(0).getCheckInAddress());
                    employeeAttendance.setCheckinLocation(checkInHistory.get(0).getCheckInlocation());
                }
                employeeAttendance.setCheckOut(checkInHistory.get(checkInHistory.size()-1).getCheckout());
                employeeAttendance.setCheckOutaddress(checkInHistory.get(checkInHistory.size()-1).getCheckOutAddress());
                employeeAttendance.setCheckoutLocation(checkInHistory.get(checkInHistory.size()-1).getCheckOutlocation());

                Long totalHours = employeeAttendance.getTotalHour();
                totalHours = totalHours+calculatedtotalHour.get();
                employeeAttendance.setTotalHour(totalHours);

                employeeAttendance = employeeAttendanceService.update(employeeAttendance);
*/
                response.put("status", "success");

            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> loadAttendance(
    @RequestParam(value = "page", defaultValue = "0") int page,
    @RequestParam(value = "size", defaultValue = "3") int size){
        Map<String,Object> response = new HashMap();
        try {
            Page<CheckInCheckOutHistory> requestedPage = null;
            requestedPage =  checkInCheckOutHistoryService.findAllAttendanceByEmployeeId(loggedIn.getUser().getEmployeId().getId(),page,size);
            response.put("totalElement", requestedPage.getTotalElements());
            response.put("totalPage", requestedPage.getTotalPages());
            response.put("numberOfelement", requestedPage.getNumberOfElements());
            response.put("currentPageNmber", requestedPage.getNumber());
            response.put("data", requestedPage.getContent());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<?> loadAttendance(
            @RequestParam(value = "employeeId", defaultValue = "0") String employeeId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size){
        Map<String,Object> response = new HashMap();
        try {
            Page<CheckInCheckOutHistory> requestedPage = null;
            requestedPage =  checkInCheckOutHistoryService.findAllAttendanceByEmployeeId(employeeId,page,size);
            response.put("totalElement", requestedPage.getTotalElements());
            response.put("totalPage", requestedPage.getTotalPages());
            response.put("numberOfelement", requestedPage.getNumberOfElements());
            response.put("currentPageNmber", requestedPage.getNumber());
            response.put("data", requestedPage.getContent());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/checkin")
    public ResponseEntity<?> checkIn(){
        return null;
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(){
        return null;
    }
}
