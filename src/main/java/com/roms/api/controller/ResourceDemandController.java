package com.roms.api.controller;

import com.roms.api.model.*;
import com.roms.api.requestInput.RecommendInput;
import com.roms.api.requestInput.ResourceDemandInput;
import com.roms.api.service.*;
import com.roms.api.utils.LoggedInUserDetails;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/jobs/resource/demand")
public class ResourceDemandController {

    @Autowired
    private EmployeeResourcedemandService employeeResourcedemandService;

    @Autowired
    private ClientProjectSubteamService clientProjectSubteamService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientContractService clientContractService;

    @Autowired
    private ClientProjectService clientProjectService;

    @Autowired
    private EmployeeSkilsCirtificateService employeeSkilsCirtificateService;
    @Autowired
    private EmployeeSkilsLicenceService employeeSkilsLicenceService;
    @Autowired
    private EmployeeSkilsPlantService employeeSkilsPlantService;

    @Autowired
    private EmploymentRecommendService employmentRecommendService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private EmployeeManagerService employeeManagerService;

    @Autowired
    private LoggedInUserDetails loggedIn;

    @PostMapping()
    public ResponseEntity<?> saveJobResourceDemand(@RequestBody ResourceDemandInput request){
        Map<String,Object> response = new HashMap();
        
        try {
        	 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Instant perposedDate = sdf.parse(request.getPerposedDate()).toInstant();
        	
            EmployeeResourcedemand employeeResourcedemand = EmployeeResourcedemand.builder()
                   .hiringManager(new Employe(request.getHiringManagerId()))
                    .demandType(request.getDemandType())
                    .roleName(request.getProfileRole())
                    .perposedDate(perposedDate)
                    .status(0)
                    .description(request.getDescription())
                    .type(request.getType()) // internal or external
                    .minimumExperiecne(request.getMinimumExperiecne())
                    .commitement(request.getCommitment())
                    .build();

            if(StringUtils.isNotBlank(request.getClientId())){
                employeeResourcedemand.setClient(new Client(request.getClientId()));

                if(StringUtils.isNotBlank(request.getClientProjectNameId())){
                    employeeResourcedemand.setClientProject( new ClientProject(request.getClientProjectNameId()));
                }

                if(StringUtils.isNotBlank(request.getClientProjectSubteamId())){
                    Optional<ClientProjectSubteam> clientProjectSubteamModel =  clientProjectSubteamService.findById(request.getClientProjectSubteamId());
                    if(clientProjectSubteamModel.isPresent()){
                        ClientProjectSubteam clientProjectSubteam = clientProjectSubteamModel.get();
                        clientProjectSubteam.setWageRole(request.getWageRole());
                        clientProjectSubteam.setAwardType(request.getAwardType());
                        clientProjectSubteam.setRate(request.getRate());
                        clientProjectSubteam.setWageClassification(request.getWageClassification());
                        clientProjectSubteam = clientProjectSubteamService.update(clientProjectSubteam);
                        employeeResourcedemand.setClientProjectSubteam(clientProjectSubteam);
                    }
                }

                if(StringUtils.isNotBlank(request.getContractId())){
                    employeeResourcedemand.setClientContract(new ClientContract(request.getContractId()));
                }
            }else{
                String clientName = request.getClientName();
                Client clientmodel = Client.builder()
                        .name(clientName)
                        .build();
                clientmodel = clientService.save(clientmodel);
                employeeResourcedemand.setClient(clientmodel);

                String contractName = request.getContractName();
                ClientContract contractModel = ClientContract.builder()
                                .name(contractName)
                                 .client(clientmodel)
                        .build();
                contractModel = clientContractService.save(contractModel);
                employeeResourcedemand.setClientContract(contractModel);

                Location locationModel = new Location();
                locationModel.setDescription(request.getLocationName());
                locationModel.setCode(request.getLocationName());
                locationModel = locationService.save(locationModel);



                String projectName = request.getClientProjectName();
                ClientProject projectModel = new ClientProject();
                projectModel.setContract(contractModel);
                projectModel.setClient(clientmodel);
                projectModel.setName(projectName);
                projectModel.setLocation(locationModel);

                projectModel = clientProjectService.save(projectModel);
                employeeResourcedemand.setClientProject(projectModel);

                ClientProjectSubteam clientProjectSubteam = new ClientProjectSubteam();
                clientProjectSubteam.setTeamName(request.getClientProjectSubteamName());
                clientProjectSubteam.setCode(request.getClientProjectSubteamName());
                clientProjectSubteam.setWageRole(request.getWageRole());
                clientProjectSubteam.setAwardType(request.getAwardType());
                clientProjectSubteam.setRate(request.getRate());
                clientProjectSubteam.setWageClassification(request.getWageClassification());
                clientProjectSubteam.setLocation(locationModel);
                clientProjectSubteam.setClientProject(projectModel);

                clientProjectSubteam = clientProjectSubteamService.update(clientProjectSubteam);
                employeeResourcedemand.setClientProjectSubteam(clientProjectSubteam);


            }

            Map<String, Object> skilsMap =  request.getSkils();
            saveOrUpdateSkills(skilsMap);
            employeeResourcedemand.setSkilsMap(request.getSkils());
            employeeResourcedemandService.save(employeeResourcedemand);
            response.put("status","success");
            response.put("id",employeeResourcedemand.getId());
            response.put("demandId",employeeResourcedemand.getDemandId());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<?> loadAllResourceDemand(){
        Map<String,Object> response = new HashMap();

        try {
            List<EmployeeResourcedemand> dataList = employeeResourcedemandService.findAllPendingDemand();
            response.put("data",dataList);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/recommend")
    public ResponseEntity<?> recomendEmployee(@RequestBody RecommendInput request){
        Map<String, Object> response = new HashMap();
        try {

            if(employmentRecommendService.alreadyRequested(request.getResourceDemandId(),request.getEmployeeId())){
                return new ResponseEntity<>("already_requested", HttpStatus.BAD_REQUEST);
            }
            EmploymentRecommendation model = new EmploymentRecommendation();
            Optional<EmployeeResourcedemand> resourcedemand = employeeResourcedemandService.findById(request.getResourceDemandId());
            if (resourcedemand.isPresent()) {
                EmployeeResourcedemand resourcedemand1 = resourcedemand.get();
                model.setDemandIdx(resourcedemand1);
                if(StringUtils.isNotBlank(request.getSubTeamId())){
                    ClientProjectSubteam fromTeam = new ClientProjectSubteam();
                    fromTeam.setId(request.getSubTeamId());
                    model.setFromSubteamIdx(fromTeam);
                }
                model.setToSubteamIdx(resourcedemand1.getClientProjectSubteam());
                Employe employe = new Employe();
                employe.setId(request.getEmployeeId());
                model.setEmployeeIdx(employe);
                model.setAcceptedBy(resourcedemand1.getHiringManager());

                model.setStatus(1); // pending
            }
            employmentRecommendService.save(model);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/employee/approve")
    public ResponseEntity<?> approveEmployee(@RequestBody RecommendInput request){
        Map<String, Object> response = new HashMap();
        try {

            EmploymentRecommendation model = new EmploymentRecommendation();
            Optional<EmployeeResourcedemand> resourcedemand = employeeResourcedemandService.findById(request.getResourceDemandId());
            if (resourcedemand.isPresent()) {
                EmployeeResourcedemand resourceDemand1 = resourcedemand.get();
                if(loggedIn.getUser().getEmployeId().getId().equalsIgnoreCase(resourceDemand1.getHiringManager().getId())){
                    List<EmploymentRecommendation>   allRecomandation =  employmentRecommendService.findByResourceDemandId(resourceDemand1.getId());
                    allRecomandation.forEach(obj->{
                        if(request.getId().equalsIgnoreCase(obj.getId())){
                            obj.setStatus(2); // approved
                        }else{
                            obj.setStatus(3); // rejected
                        }
                        employmentRecommendService.update(obj);
                    });

                }else{
                    return new ResponseEntity<>("not_authorised", HttpStatus.BAD_REQUEST);
                }

                resourceDemand1.setStatus(1);
                employeeResourcedemandService.update(resourceDemand1);
            }
            return new ResponseEntity<>("success", HttpStatus.OK);
        }catch (Exception e){
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/employee/reject")
    public ResponseEntity<?> reject(@RequestBody RecommendInput request){
                Map<String, Object> response = new HashMap();
        try {

            EmploymentRecommendation model = new EmploymentRecommendation();
            Optional<EmployeeResourcedemand> resourcedemand = employeeResourcedemandService.findById(request.getResourceDemandId());
            if (resourcedemand.isPresent()) {
                EmployeeResourcedemand resourceDemand1 = resourcedemand.get();
                if(loggedIn.getUser().getEmployeId().getId().equalsIgnoreCase(resourceDemand1.getHiringManager().getId())){
                    Optional<EmploymentRecommendation> recommendation =  employmentRecommendService.findById(request.getId());
                    if(recommendation.isPresent()){
                        EmploymentRecommendation employmentRecommendation =  recommendation.get();
                        employmentRecommendation.setStatus(3);
                        employmentRecommendService.update(employmentRecommendation);
                    }
                }else{
                    return new ResponseEntity<>("not_authorised", HttpStatus.BAD_REQUEST);
                }
            }
            return new ResponseEntity<>("success", HttpStatus.OK);
        }catch (Exception e){
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/recommend")
    public ResponseEntity<?> getRecomendEmployee(){
        Map<String,Object> response = new HashMap();
        List<Map<String,Object>> dataList = new ArrayList<>();
        EmploymentRecommendation model = new EmploymentRecommendation();
        List<EmploymentRecommendation> recommendList = employmentRecommendService.findAllByPendingDemands();
        recommendList.forEach(obj->{
            Map<String,Object> response1 = new HashMap();
            response1.put("recommendDetails",obj);
            Optional<EmployeeManagers> managers = employeeManagerService.getManager(obj.getEmployeeIdx().getId());
            if(managers.isEmpty()){
                response1.put("manager",null);
            }else {
                response1.put("manager",managers.get().getManagers());
            }
            dataList.add(response1);
        });

        response.put("data",dataList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/recommend/{resouceDemandId}")
    public ResponseEntity<?> getRecomendEmployee(@PathVariable("resouceDemandId") String demandId){
        Map<String,Object> response = new HashMap();
        List<Map<String,Object>> dataList = new ArrayList<>();
        EmploymentRecommendation model = new EmploymentRecommendation();
        List<EmploymentRecommendation> recommendList = employmentRecommendService.findByResourceDemandId(demandId);
        recommendList.forEach(obj->{
            Map<String,Object> response1 = new HashMap();
            response1.put("recommendDetails",obj);
            Optional<EmployeeManagers> managers = employeeManagerService.getManager(obj.getEmployeeIdx().getId());
            if(managers.isEmpty()){
                response1.put("manager",null);
            }else {
                response1.put("manager",managers.get().getManagers());
            }
            dataList.add(response1);
        });

        response.put("data",dataList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/approved/report")
    public ResponseEntity<?> loadAllApprovedReport(){
        Map<String,Object> response = new HashMap();
        List<Map<String,Object>> dataList = new ArrayList<>();
        EmploymentRecommendation model = new EmploymentRecommendation();
        List<EmploymentRecommendation> recommendList = employmentRecommendService.findAllApprovedReport();
        recommendList.forEach(obj->{
            Map<String,Object> response1 = new HashMap();
            response1.put("recommendDetails",obj);
            Optional<EmployeeManagers> managers = employeeManagerService.getManager(obj.getEmployeeIdx().getId());
            if(managers.isEmpty()){
                response1.put("manager",null);
            }else {
                response1.put("manager",managers.get().getManagers());
            }
            dataList.add(response1);
        });
        response.put("data",dataList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public void saveOrUpdateSkills(Map<String, Object> skilsMap){

       Map<String,String>  certification  = (Map<String,String>) skilsMap.get("certification");
         Map<String,String> licenses  = (Map<String,String>) skilsMap.get("licenses");
         Map<String,String> plantTicket  = (Map<String,String>) skilsMap.get("plantTicket");

        for(String key :certification.keySet() ){
            String val  = certification.get(key);
            EmployeeSkilsCirtificate model = new EmployeeSkilsCirtificate ();
            model.setName(val);
            model.setCode(key);
            employeeSkilsCirtificateService.save(model);
        }

        for(String key :licenses.keySet() ){
            String val  = licenses.get(key);
            EmployeeSkilsLicence model = new EmployeeSkilsLicence ();
            model.setName(val);
            model.setCode(key);
            employeeSkilsLicenceService.save(model);
        }

        for(String key :plantTicket.keySet() ){
            String val  = plantTicket.get(key);
            EmployeeSkilsPlant model = new EmployeeSkilsPlant ();
            model.setName(val);
            model.setCode(key);
            employeeSkilsPlantService.save(model);
        }

    }

    @GetMapping("/{demandId}")
    public ResponseEntity<?> loadResourceDemandById(@PathVariable("demandId") String demandId){
        Map<String,Object> response = new HashMap();

        try {
            Optional<EmployeeResourcedemand> dataList = employeeResourcedemandService.findById(demandId);
            if(!dataList.isEmpty()) {
            	response.put("data",dataList.get());
            }else {
            	 return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/mydemand/{employeeId}")
    public ResponseEntity<?> loadAllResourceDemandByEmployee(@PathVariable("employeeId") String employeeId){
        Map<String,Object> response = new HashMap();

        try {
            List<EmployeeResourcedemand> dataList = employeeResourcedemandService.findbyEmployeeId(employeeId);
            response.put("data",dataList);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
