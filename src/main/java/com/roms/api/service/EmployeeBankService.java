package com.roms.api.service;

import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeBanks;
import com.roms.api.repository.EmployeeBankRepository;
import com.roms.api.requestInput.OnboardingBankingInput;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeBankService {

    @Autowired
    private EmployeeBankRepository employeeBankRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;

    public EmployeeBanks save(EmployeeBanks employeeBanks){
        employeeBanks.setOrganisation(loggedIn.getOrg());
        employeeBanks.setCreateBy(loggedIn.getUser());
        employeeBanks.setCreateDate(Instant.now());
        return employeeBankRepository.save(employeeBanks);
    }

    public EmployeeBanks saveFromOnboarding(OnboardingBankingInput request){
        EmployeeBanks employeeBanks1 = EmployeeBanks.builder()
                .secondaryAccount(request.getSecondaryAccount())
                .payslipByEmail(request.getPayslipByEmail())
                .employe(loggedIn.getUser().getEmployeId())
                .bankType(1)
                .bankName(request.getDefaultBank().getBankName())
                .accountHolderName(request.getDefaultBank().getAccountHolderName())
                .accountNumber(request.getDefaultBank().getAccountNumber())
                .bsbNumber(request.getDefaultBank().getBsbNumber())
                .fixedAmount(request.getDefaultBank().getFixedAmount())
                .build();
        Optional<EmployeeBanks> employeeBanks =employeeBankRepository.findByEmployeAndOrganisationAndBankType(loggedIn.getUser().getEmployeId(),loggedIn.getOrg(),1);
        if(employeeBanks.isPresent()){
            employeeBanks1.setId(employeeBanks.get().getId());
        }
        employeeBanks1 =  save(employeeBanks1);
        if(request.getSecondaryAccount() == 1){
            EmployeeBanks employeeBanks2 = EmployeeBanks.builder()
                    .secondaryAccount(request.getSecondaryAccount())
                    .payslipByEmail(request.getPayslipByEmail())
                    .employe(loggedIn.getUser().getEmployeId())
                    .bankType(2)
                    .bankName(request.getSecondaryBank().getBankName())
                    .accountHolderName(request.getSecondaryBank().getAccountHolderName())
                    .accountNumber(request.getSecondaryBank().getAccountNumber())
                    .bsbNumber(request.getSecondaryBank().getBsbNumber())
                    .fixedAmount(request.getSecondaryBank().getFixedAmount())
                    .build();
             employeeBanks =employeeBankRepository.findByEmployeAndOrganisationAndBankType(loggedIn.getUser().getEmployeId(),loggedIn.getOrg(),2);
            if(employeeBanks.isPresent()){
                employeeBanks2.setId(employeeBanks.get().getId());
            }
            return save(employeeBanks2);
        }
        return  employeeBanks1;
    }

    public List<EmployeeBanks> findBankDetailsByEmployee(String employeeId){
        return employeeBankRepository.findAllByEmployeAndOrganisation(new Employe(employeeId), loggedIn.getOrg());
    }
}
