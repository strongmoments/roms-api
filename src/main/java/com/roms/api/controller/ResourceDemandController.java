package com.roms.api.controller;

import com.roms.api.model.*;
import com.roms.api.requestInput.ResourceDemandInput;
import com.roms.api.service.*;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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

    @PostMapping()
    public ResponseEntity<?> saveJobResourceDemand(@RequestBody ResourceDemandInput request){
        Map<String,Object> response = new HashMap();
        try {
            EmployeeResourcedemand employeeResourcedemand = EmployeeResourcedemand.builder()
                   .hiringManager(new Employe(request.getHiringManagerId()))
                    .demandType(request.getDemandType())
                    .roleName(request.getProfileRole())
                    //.perposedDate()
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

                String projectName = request.getClientProjectName();
                ClientProject projectModel = new ClientProject();
                projectModel.setContract(contractModel);
                projectModel.setClient(clientmodel);
                projectModel.setName(projectName);

                projectModel = clientProjectService.save(projectModel);
                employeeResourcedemand.setClientProject(projectModel);

                ClientProjectSubteam clientProjectSubteam = new ClientProjectSubteam();
                clientProjectSubteam.setTeamName(request.getClientProjectSubteamName());
                clientProjectSubteam.setCode(request.getClientProjectSubteamName());
                clientProjectSubteam.setWageRole(request.getWageRole());
                clientProjectSubteam.setAwardType(request.getAwardType());
                clientProjectSubteam.setRate(request.getRate());
                clientProjectSubteam.setWageClassification(request.getWageClassification());
                clientProjectSubteam = clientProjectSubteamService.update(clientProjectSubteam);
                employeeResourcedemand.setClientProjectSubteam(clientProjectSubteam);
            }

            Map<String, Object> skilsMap =  request.getSkils();
            saveOrUpdateSkills(skilsMap);
            employeeResourcedemand.setSkilsMap(request.getSkils());
            employeeResourcedemandService.save(employeeResourcedemand);
            response.put("status","success");
            response.put("id",employeeResourcedemand.getId());
            response.put("demandId",employeeResourcedemand.getId());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

    @GetMapping("/{employeeId}")
    public ResponseEntity<?> loadAllResourceDemand(@PathVariable("employeeId") String employeeId){
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
