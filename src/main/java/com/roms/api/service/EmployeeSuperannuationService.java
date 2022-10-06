package com.roms.api.service;

import com.roms.api.model.DigitalAssets;
import com.roms.api.model.EmployeeSuperannuation;
import com.roms.api.repository.EmployeeSuperannuationRepository;
import com.roms.api.requestInput.OnboardingSuperannuationInput;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

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

        EmployeeSuperannuation EmployeeSuperannuationCurrentFund = EmployeeSuperannuation.builder()
                .fillSuperFundNow(request.isFillSuperFundNow())
                .signature(digitalAssetsSignature)
                .paidAsperMychoice(request.isPaidAsperMychoice())
                .abn(request.getCurrentFund().getAbn())
                //.date(request.getDate())
                .currentFundAttachment(digitalAssetCurrentFundAttachment)
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

            save(EmployeeSuperannuationCurrentFund);

        EmployeeSuperannuation employeeSuperannuationSelfFund = EmployeeSuperannuation.builder()
                .fillSuperFundNow(request.isFillSuperFundNow())
                .signature(digitalAssetsSignature)
                .paidAsperMychoice(request.isPaidAsperMychoice())
                .abn(request.getSelfManagedFund().getAbn())
                //.date(request.getDate())
                .selfFundattachment(digitalAssetSelfFundattachment)
               //
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

        return save(employeeSuperannuationSelfFund);


    }
}
