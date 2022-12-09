package com.roms.api.controller;

import com.roms.api.model.*;
import com.roms.api.requestInput.RecommendInput;
import com.roms.api.requestInput.ResourceDemandInput;
import com.roms.api.service.*;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

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

    @PostMapping("/recommend")
    public ResponseEntity<?> recomendEmployee(@RequestBody RecommendInput request){
        Map<String, Object> response = new HashMap();
        try {

            EmploymentRecommendation model = new EmploymentRecommendation();
            Optional<EmployeeResourcedemand> resourcedemand = employeeResourcedemandService.findById(request.getResourceDemandId());
            if (resourcedemand.isPresent()) {
                EmployeeResourcedemand resourcedemand1 = resourcedemand.get();
                model.setDemandIdx(resourcedemand1);
                ClientProjectSubteam fromTeam = new ClientProjectSubteam();
                fromTeam.setId(request.getSubTeamId());
                model.setToSubteamIdx(resourcedemand1.getClientProjectSubteam());
                model.setFromSubteamIdx(fromTeam);
                Employe employe = new Employe();
                employe.setId(request.getEmployeeId());
                model.setEmployeeIdx(employe);
                model.setAcceptedBy(resourcedemand1.getHiringManager());

                model.setStatus(1);
            }
            employmentRecommendService.save(model);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/recommend")
    public ResponseEntity<?> getRecomendEmployee(){
        Map<String,Object> response = new HashMap();
        EmploymentRecommendation model = new EmploymentRecommendation();
        List<EmploymentRecommendation> recommendList = employmentRecommendService.findAll();
        response.put("data",recommendList);
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

    @GetMapping()
    public ResponseEntity<?> loadAllResourceDemand(){
        Map<String,Object> response = new HashMap();

        try {
            List<EmployeeResourcedemand> dataList = employeeResourcedemandService.findAll();
            response.put("data",dataList);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

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
