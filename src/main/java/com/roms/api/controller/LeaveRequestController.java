package com.roms.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roms.api.dto.FileDto;
import com.roms.api.model.*;
import com.roms.api.requestInput.LeaveRequestSearchInput;
import com.roms.api.service.*;
import com.roms.api.utils.LoggedInUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/leave")
public class LeaveRequestController {
    public static final Logger logger = LoggerFactory.getLogger(LeaveRequestController.class);

    @Autowired
    private LeaveRequestService leaveRequestService;

    @Autowired
    private LeaveTypeService leaveTypeService;

    @Autowired
    private LoggedInUserDetails loggedIn;

    @Autowired
    private ClientProjectSubteamMemberService clientProjectSubteamMemberService;

    @Autowired
    @Qualifier("leavenotification")
    private NotificationService notificationService;

    @Autowired
    private LeaveAttachmentService leaveAttachmentService;


    @Autowired
    private MinioService minioService;


    @RequestMapping(value = "/uploaddoc", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam String leaveRequestId, @RequestParam(value = "files") MultipartFile[] filse) throws IOException {

        Map<String, Object> response = new HashMap();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, MultipartFile> imageData = new HashMap();
        try {
            List<DigitalAssets> digitalAsset = new ArrayList<>();
            for (MultipartFile file : filse) {
                FileDto fileDto = FileDto.builder()
                        .file(file).build();
                DigitalAssets digitalAssets = minioService.uploadFile(fileDto);
                LeaveRequest leaveRequest = new LeaveRequest();
                leaveRequest.setId(leaveRequestId);
                LeaveAttachments leaveAttachments = LeaveAttachments.builder().digitalAssets(digitalAssets).leaveRequestId(leaveRequest).build();
                leaveAttachmentService.save(leaveAttachments);
            }
            response.put("status", "success");

        } catch (Exception ee) {
            ee.printStackTrace();
            response.put("status", "error");
            response.put("error", ee.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/request", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> requestLeave(@RequestBody LeaveRequest leaveRequest) throws ParseException {
        Map<String, Object> response = new HashMap<>();
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
            Instant i = sdf.parse(leaveRequest.getStrStartDateTime()).toInstant();
            leaveRequest.setStartDateTime(sdf.parse(leaveRequest.getStrStartDateTime()).toInstant());
            leaveRequest.setEndDateTime(sdf.parse(leaveRequest.getStrEndDateTime()).toInstant());
            LeaveRequest leaveRequests = leaveRequestService.applyLeave(leaveRequest);
            if (leaveRequest != null && leaveRequest.getId() != null) {
                notificationService.sendNotification(leaveRequest.getId());
            }
            response.put("leaveRequestId", leaveRequest.getId());
        } catch (Exception e) {
            logger.error("Error while applying leave {} ", e.getMessage());
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        response.put("status", "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/approve", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> approveLeave(@RequestBody LeaveRequest leaveRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            leaveRequest = leaveRequestService.approveLeave(leaveRequest);
            if (leaveRequest != null && leaveRequest.getId() != null)
                notificationService.sendApprovedOrRejectNotification(leaveRequest.getId(), "approved your", "approve");
        } catch (Exception e) {
            logger.error("Error while approving leave {} ", e.getMessage());
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("status", "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/reject", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> rejectLeave(@RequestBody LeaveRequest leaveRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            leaveRequest = leaveRequestService.rejectLeave(leaveRequest);
            if (leaveRequest != null && leaveRequest.getId() != null)
                notificationService.sendApprovedOrRejectNotification(leaveRequest.getId(), "rejected your", "reject");
        } catch (Exception e) {
            logger.error("Error while rejecting leave {} ", e.getMessage());
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("status", "success");
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // 0-pendint, 1-approved, 2-rejected
    @GetMapping(value = "/applied")
    public ResponseEntity<?> loadApliedLeaveByLeaveStatus(
            @RequestParam(value = "leaveStatus", defaultValue = "0") int leaveStatus,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size) {
        String employeeId = loggedIn.getUser().getEmployeId().getId();

        Map<String, Object> response = new HashMap<>();
        try {
            Page<LeaveRequest> requestedPage = null;
            if (leaveStatus == 0) {
                requestedPage = leaveRequestService.findAllSentRequest(employeeId, page, size);
            } else {
                requestedPage = leaveRequestService.findAllSentRequestByLeaveStatus(employeeId, leaveStatus, page, size);
            }
            response.put("totalElement", requestedPage.getTotalElements());
            response.put("totalPage", requestedPage.getTotalPages());
            response.put("numberOfelement", requestedPage.getNumberOfElements());
            response.put("currentPageNmber", requestedPage.getNumber());
            response.put("data", requestedPage.getContent());
        } catch (Exception e) {
            logger.error("An error occurred! {}", e.getMessage());
            response.put("status", "error");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/appliedToMe")
    public ResponseEntity<?> loadApliedToMeLeaveByLeaveStatus(
            @RequestParam(value = "leaveStatus", defaultValue = "0") int leaveStatus,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size) {

        String employeeId = loggedIn.getUser().getEmployeId().getId();

        Map<String, Object> response = new HashMap<>();
        try {
            Page<LeaveRequest> requestedPage = null;
            if (leaveStatus == 0) {
                requestedPage = leaveRequestService.findAllRecievedRequest(employeeId, page, size);
            } else {
                requestedPage = leaveRequestService.findAllRecievedRequestByLeaveStatus(employeeId, leaveStatus, page, size);
            }
            response.put("totalElement", requestedPage.getTotalElements());
            response.put("totalPage", requestedPage.getTotalPages());
            response.put("numberOfelement", requestedPage.getNumberOfElements());
            response.put("currentPageNmber", requestedPage.getNumber());
            response.put("data", requestedPage.getContent());
        } catch (Exception e) {
            logger.error("An error occurred! {}", e.getMessage());
            response.put("status", "error");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/appliedToMeHistory")
    public ResponseEntity<?> loadApliedToMeLeaveHistory(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size) {

        String employeeId = loggedIn.getUser().getEmployeId().getId();

        Map<String, Object> response = new HashMap<>();
        try {
            Page<LeaveRequest> requestedPage = null;

            requestedPage = leaveRequestService.findAllRecievedRequestHistory(employeeId, page, size);

            response.put("totalElement", requestedPage.getTotalElements());
            response.put("totalPage", requestedPage.getTotalPages());
            response.put("numberOfelement", requestedPage.getNumberOfElements());
            response.put("currentPageNmber", requestedPage.getNumber());
            response.put("data", requestedPage.getContent());
        } catch (Exception e) {
            logger.error("An error occurred! {}", e.getMessage());
            response.put("status", "error");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(value = "/types")
    public ResponseEntity<?> loadLeaveType() {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("data", leaveTypeService.findAll());
        } catch (Exception e) {
            logger.error("An error occurred! {}", e.getMessage());
            response.put("status", "error");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/approver")
    public ResponseEntity<?> loadLeveApprover() {
        Map<String, Object> response = new HashMap<>();
        try {
            return new ResponseEntity<>(clientProjectSubteamMemberService.getLeaveApprover(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred! {}", e.getMessage());
            response.put("status", "error");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/loadAll")
    public ResponseEntity<?> loadAll(
            @RequestBody LeaveRequestSearchInput leaveRequestSearchInput,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Instant fromDate = sdf.parse(leaveRequestSearchInput.getFromDate()).toInstant();
        Instant toDate = sdf.parse(leaveRequestSearchInput.getToDate()).toInstant();
        Integer leaveStatus = Integer.parseInt(leaveRequestSearchInput.getStatus());

        Map<String, Object> response = new HashMap<>();
        try {
            Page<LeaveRequest> requestedPage = null;
            if (!(StringUtils.isEmpty(leaveRequestSearchInput.getDepartmentId())) && !(StringUtils.isEmpty(leaveRequestSearchInput.getEmployeeTypeId())) && leaveStatus > 0) {
                requestedPage = leaveRequestService.findAllLeaveTransactionByEmployeeTypeAndDepartmentAndLeaveStatus(page, size, fromDate, toDate, leaveRequestSearchInput.getEmployeeTypeId(), leaveRequestSearchInput.getDepartmentId(), leaveStatus);
            } else if (!(StringUtils.isEmpty(leaveRequestSearchInput.getDepartmentId())) && StringUtils.isEmpty(leaveRequestSearchInput.getEmployeeTypeId()) && leaveStatus > 0) {
                requestedPage = leaveRequestService.findAllLeaveTransactionByDepartmentAndLeaveStatus(page, size, fromDate, toDate, leaveRequestSearchInput.getDepartmentId(), leaveStatus);
            } else if (StringUtils.isEmpty(leaveRequestSearchInput.getDepartmentId()) && !(StringUtils.isEmpty(leaveRequestSearchInput.getEmployeeTypeId())) && leaveStatus > 0) {
                requestedPage = leaveRequestService.findAllLeaveTransactionByEmployeeTypeAndLeaveStatus(page, size, fromDate, toDate, leaveRequestSearchInput.getEmployeeTypeId(), leaveStatus);
            } else if (!(StringUtils.isEmpty(leaveRequestSearchInput.getDepartmentId())) && !(StringUtils.isEmpty(leaveRequestSearchInput.getEmployeeTypeId()))) {
                requestedPage = leaveRequestService.findAllLeaveTransactionByEmployeeTypeAndDepartment(page, size, fromDate, toDate, leaveRequestSearchInput.getEmployeeTypeId(), leaveRequestSearchInput.getDepartmentId());
            } else if (!(StringUtils.isEmpty(leaveRequestSearchInput.getDepartmentId())) && StringUtils.isEmpty(leaveRequestSearchInput.getEmployeeTypeId())) {
                requestedPage = leaveRequestService.findAllLeaveTransactionByDepartment(page, size, fromDate, toDate, leaveRequestSearchInput.getDepartmentId());
            } else if (StringUtils.isEmpty(leaveRequestSearchInput.getDepartmentId()) && !(StringUtils.isEmpty(leaveRequestSearchInput.getEmployeeTypeId()))) {
                requestedPage = leaveRequestService.findAllLeaveTransactionByEmployeeType(page, size, fromDate, toDate, leaveRequestSearchInput.getEmployeeTypeId());
            } else {
                if (leaveStatus > 0) {
                    requestedPage = leaveRequestService.findAllLeaveTransactionByLeaveStatus(page, size, fromDate, toDate, leaveStatus);
                } else {
                    requestedPage = leaveRequestService.findAllLeaveTransaction(page, size, fromDate, toDate);
                }

            }

            response.put("totalElement", requestedPage.getTotalElements());
            response.put("totalPage", requestedPage.getTotalPages());
            response.put("numberOfelement", requestedPage.getNumberOfElements());
            response.put("currentPageNmber", requestedPage.getNumber());
            response.put("data", requestedPage.getContent());
        } catch (Exception e) {
            logger.error("An error occurred! {}", e.getMessage());
            response.put("status", "error");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping(value = "/loadCurrent")
    public ResponseEntity<?> loadCurrentLeaves(
            @RequestBody LeaveRequestSearchInput leaveRequestSearchInput,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Instant fromDate = sdf.parse(leaveRequestSearchInput.getFromDate()).toInstant();
        Instant toDate = sdf.parse(leaveRequestSearchInput.getToDate()).toInstant();
        Integer leaveStatus = Integer.parseInt(leaveRequestSearchInput.getStatus());

        Map<String, Object> response = new HashMap<>();
        try {
            Page<LeaveRequest> requestedPage = null;
            if (!(StringUtils.isEmpty(leaveRequestSearchInput.getDepartmentId())) && !(StringUtils.isEmpty(leaveRequestSearchInput.getEmployeeTypeId())) && leaveStatus > 0) {
                requestedPage = leaveRequestService.findAllCurrentLeaveTransactionByEmployeeTypeAndDepartmentAndLeaveStatus(page, size, fromDate, toDate, leaveRequestSearchInput.getEmployeeTypeId(), leaveRequestSearchInput.getDepartmentId(), leaveStatus);
            } else if (!(StringUtils.isEmpty(leaveRequestSearchInput.getDepartmentId())) && StringUtils.isEmpty(leaveRequestSearchInput.getEmployeeTypeId()) && leaveStatus > 0) {
                requestedPage = leaveRequestService.findAllCurrentLeaveTransactionByDepartmentAndLeaveStatus(page, size, fromDate, toDate, leaveRequestSearchInput.getDepartmentId(), leaveStatus);
            } else if (StringUtils.isEmpty(leaveRequestSearchInput.getDepartmentId()) && !(StringUtils.isEmpty(leaveRequestSearchInput.getEmployeeTypeId())) && leaveStatus > 0) {
                requestedPage = leaveRequestService.findAllCurrentLeaveTransactionByEmployeeTypeAndLeaveStatus(page, size, fromDate, toDate, leaveRequestSearchInput.getEmployeeTypeId(), leaveStatus);
            } else if (!(StringUtils.isEmpty(leaveRequestSearchInput.getDepartmentId())) && !(StringUtils.isEmpty(leaveRequestSearchInput.getEmployeeTypeId()))) {
                requestedPage = leaveRequestService.findAllCurrentLeaveTransactionByEmployeeTypeAndDepartment(page, size, fromDate, toDate, leaveRequestSearchInput.getEmployeeTypeId(), leaveRequestSearchInput.getDepartmentId());
            } else if (!(StringUtils.isEmpty(leaveRequestSearchInput.getDepartmentId())) && StringUtils.isEmpty(leaveRequestSearchInput.getEmployeeTypeId())) {
                requestedPage = leaveRequestService.findAllCurrentLeaveTransactionByDepartment(page, size, fromDate, toDate, leaveRequestSearchInput.getDepartmentId());
            } else if (StringUtils.isEmpty(leaveRequestSearchInput.getDepartmentId()) && !(StringUtils.isEmpty(leaveRequestSearchInput.getEmployeeTypeId()))) {
                requestedPage = leaveRequestService.findAllCurrentLeaveTransactionByEmployeeType(page, size, fromDate, toDate, leaveRequestSearchInput.getEmployeeTypeId());
            } else {
                if (leaveStatus > 0) {
                    requestedPage = leaveRequestService.findAllCurrentLeaveTransactionByLeaveStatus(page, size, fromDate, toDate, leaveStatus);
                } else {
                    requestedPage = leaveRequestService.findAllCurrentLeaveTransaction(page, size, fromDate, toDate);
                }

            }

            response.put("totalElement", requestedPage.getTotalElements());
            response.put("totalPage", requestedPage.getTotalPages());
            response.put("numberOfelement", requestedPage.getNumberOfElements());
            response.put("currentPageNmber", requestedPage.getNumber());
            response.put("data", requestedPage.getContent());
        } catch (Exception e) {
            logger.error("An error occurred! {}", e.getMessage());
            response.put("status", "error");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/process", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestBody LeaveRequest leaveRequest) throws IOException {
        Map<String, Object> response = new HashMap<>();
        Optional<LeaveRequest> model = leaveRequestService.findById(leaveRequest.getId());
        if (model.isPresent()) {
            LeaveRequest leaveModel = model.get();
            leaveModel.setSalaryProcessStatus(leaveRequest.getSalaryProcessStatus());
            leaveModel = leaveRequestService.update(leaveModel);
            response.put("id", leaveModel.getId());
            response.put("status", "success");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
