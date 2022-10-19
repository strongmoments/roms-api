package com.roms.api.service;

import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeTFN;
import com.roms.api.repository.EmployeeTFNRepository;
import com.roms.api.requestInput.OnboardingTFNInput;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class EmployeeTFNService {
    @Autowired
    private EmployeeTFNRepository employeeTFNRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;

    public EmployeeTFN save(EmployeeTFN employeeTFN){
        employeeTFN.setOrganisation(loggedIn.getOrg());
        employeeTFN.setCreateBy(loggedIn.getUser());
        employeeTFN.setCreateDate(Instant.now());
        return  employeeTFNRepository.save(employeeTFN);
    }

    public EmployeeTFN saveFromOnboarding(OnboardingTFNInput request){
        EmployeeTFN employeeTFN1 = EmployeeTFN.builder()
                .haveTFN(request.isHaveTFN())
                .employe(loggedIn.getUser().getEmployeId())

                .TFNNumber(request.getTFNNumber())
                .claimTaxfreeThreshold(request.isClaimTaxfreeThreshold())
                .haveanyDebt(request.isHaveanyDebt())
                .taxPayerType(request.getTaxPayerType())
                .tncAcceptance(request.isTncAcceptance())
                .taxPayerType(request.getTaxPayerType())
                .build();
        return save(employeeTFN1);

    }

    public List<EmployeeTFN> findTFNbyEmployeeId(String employeeId){
       return  employeeTFNRepository.findAllByEmployeAndOrganisation(new Employe(employeeId),loggedIn.getOrg());
    }

}
