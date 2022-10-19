package com.roms.api.service;

import com.roms.api.model.DigitalAssets;
import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeSuperannuation;
import com.roms.api.repository.EmployeeSuperannuationRepository;
import com.roms.api.requestInput.OnboardingSuperannuationInput;
import com.roms.api.utils.LoggedInUserDetails;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class EmployeeSuperannuationService {

    @Autowired
    private EmployeeSuperannuationRepository employeeSuperannuationRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;

    public EmployeeSuperannuation save(EmployeeSuperannuation employeeSuperannuation){
        employeeSuperannuation.setCreateBy(loggedIn.getUser());
        employeeSuperannuation.setOrganisation(loggedIn.getOrg());
        employeeSuperannuation.setCreateDate(Instant.now());
        return employeeSuperannuationRepository.save(employeeSuperannuation);
    }

    public EmployeeSuperannuation saveFromOnboarding(OnboardingSuperannuationInput request){
        DigitalAssets digitalAssetsSignature = DigitalAssets.builder().build();
        digitalAssetsSignature.setId(request.getSignatureId());

        DigitalAssets digitalAssetCurrentFundAttachment = DigitalAssets.builder().build();
        digitalAssetCurrentFundAttachment.setId(request.getCurrentFund().getAttachmentId());

        DigitalAssets digitalAssetSelfFundattachment= DigitalAssets.builder().build();
        digitalAssetSelfFundattachment.setId(request.getSelfManagedFund().getAttachmentId());

        EmployeeSuperannuation employeeSuperannuationCurrentFund = EmployeeSuperannuation.builder()
                .fillSuperFundNow(request.isFillSuperFundNow())
                .paidAsperMychoice(request.isPaidAsperMychoice())
                .abn(request.getCurrentFund().getAbn())
                .employe(loggedIn.getUser().getEmployeId())
                //.date(request.getDate())

                .membername(request.getCurrentFund().getMembername())
                .fundType(1)
                .accountName(request.getCurrentFund().getAccountName())
                .usi(request.getCurrentFund().getUsi())
                .fundPhone(request.getCurrentFund().getFundPhone())
                .postCode(request.getCurrentFund().getPostCode())
                .state(request.getCurrentFund().getState())
                .suburb(request.getCurrentFund().getSuburb())
                .fundName(request.getCurrentFund().getFundName())
                .address(request.getCurrentFund().getAddress())
                .build();
           if(StringUtils.isNotBlank(request.getSignatureId())){
               employeeSuperannuationCurrentFund.setSignature(digitalAssetsSignature);
           }
            if(StringUtils.isNotBlank(request.getCurrentFund().getAttachmentId())){
                employeeSuperannuationCurrentFund.setCurrentFundAttachment(digitalAssetCurrentFundAttachment);
            }


            save(employeeSuperannuationCurrentFund);

        EmployeeSuperannuation employeeSuperannuationSelfFund = EmployeeSuperannuation.builder()
                .fillSuperFundNow(request.isFillSuperFundNow())

                .paidAsperMychoice(request.isPaidAsperMychoice())
                .abn(request.getSelfManagedFund().getAbn())
                .employe(loggedIn.getUser().getEmployeId())
                //.date(request.getDate())

                .fundType(2)
                .accountNumber(request.getSelfManagedFund().getAccountNumber())
                .esa(request.getSelfManagedFund().getEsa())

                .fundPhone(request.getSelfManagedFund().getFundPhone())
                .postCode(request.getSelfManagedFund().getPostCode())
                .state(request.getSelfManagedFund().getState())
                .suburb(request.getSelfManagedFund().getSuburb())
                .fundName(request.getSelfManagedFund().getFundName())
                .address(request.getSelfManagedFund().getAddress())
                .bsbCode(request.getSelfManagedFund().getBsbCode())
                .esa(request.getSelfManagedFund().getEsa())

                .build();

        if(StringUtils.isNotBlank(request.getSignatureId())){
            employeeSuperannuationSelfFund.setSignature(digitalAssetsSignature);
        }

        if(StringUtils.isNotBlank(request.getSelfManagedFund().getAttachmentId())){
            employeeSuperannuationSelfFund.setSelfFundattachment(digitalAssetSelfFundattachment);
        }

        return save(employeeSuperannuationSelfFund);


    }

    public List<EmployeeSuperannuation> findSuperAnnuationByEmployeeId(String employeeId){
        return employeeSuperannuationRepository.findAllByEmployeAndOrganisation(new Employe(employeeId),loggedIn.getOrg());
    }
}
