package com.roms.api.service;


import com.roms.api.model.DigitalAssets;
import com.roms.api.model.EmployeeLicence;
import com.roms.api.repository.EmployeeLicenceRepository;
import com.roms.api.requestInput.OnboardingLicenceInput;
import com.roms.api.utils.LoggedInUserDetails;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Locale;
import java.util.Optional;

@Service
public class EmployeeLicenceService {

    @Autowired
    private EmployeeLicenceRepository employeeLicenceRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;

    public EmployeeLicence save(EmployeeLicence employeeLicence){
        employeeLicence.setOrganisation(loggedIn.getOrg());
        employeeLicence.setCreateBy(loggedIn.getUser());
        employeeLicence.setCreateDate(Instant.now());
        return  employeeLicenceRepository.save(employeeLicence);
    }

    public EmployeeLicence findByEmployee(){

    Optional<EmployeeLicence> employeeLicenceResulst =   employeeLicenceRepository.findByEmployeAndOrganisation(loggedIn.getUser().getEmployeId(), loggedIn.getOrg());
            if(employeeLicenceResulst.isPresent()){
                return employeeLicenceResulst.get();
            }
            return  null;
    }

    public EmployeeLicence saveOnboarding(OnboardingLicenceInput request){
        EmployeeLicence employeeLicence =  findByEmployee();

        DigitalAssets digitalAssetsFrontImage = DigitalAssets.builder().build();
        digitalAssetsFrontImage.setId(request.getLicenceImageId());

        DigitalAssets digitalAssetsBackImage = DigitalAssets.builder().build();
        digitalAssetsBackImage.setId(request.getLicenceBackImageId());

        DigitalAssets digitalAssetsSignature = DigitalAssets.builder().build();
        digitalAssetsSignature.setId(request.getSignatureImageId());

        EmployeeLicence employeeLicenceModel = EmployeeLicence.builder()
                .issuedIn(request.getIssuedIn())
                .licenceNumber(request.getLicenceNumber())
                .declarationStatus(request.getDeclarationStatus())
                .employe(loggedIn.getUser().getEmployeId())
                .build();
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Instant exireDate = sdf.parse(request.getExpiryDate()).toInstant();
            employeeLicenceModel.setExpiryDate(exireDate);
        }catch (Exception e){

        }
        if(StringUtils.isNotBlank(request.getLicenceImageId())){
            employeeLicenceModel.setLicenceFrontImage(digitalAssetsFrontImage);
        }

        if(StringUtils.isNotBlank(request.getLicenceBackImageId())){
            employeeLicenceModel.setLicenceBackImage(digitalAssetsBackImage);
        }

        if(StringUtils.isNotBlank(request.getSignatureImageId())){
            employeeLicenceModel.setSignature(digitalAssetsSignature);
        }
        if(employeeLicence != null){
            employeeLicenceModel.setId(employeeLicence.getId());
        }

        return  save(employeeLicenceModel);

    }


}
