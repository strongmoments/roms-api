package com.roms.api.service;

import com.roms.api.model.Prestart;
import com.roms.api.model.PrestartDetails;
import com.roms.api.repository.PrestartDetailsRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PrestartDetailService {

    @Autowired
    private PrestartDetailsRepository prestartDetailsRepository;
    @Autowired
    private LoggedInUserDetails loggedIn;

    public PrestartDetails save(PrestartDetails model){
         model.setCreateBy(loggedIn.getUser());
         model.setCreateDate(Instant.now());
         model.setOrganisation(loggedIn.getOrg());
        return  prestartDetailsRepository.save(model);
    }

    public void deletePrestartDetailsByPrestartId(String prestartId){

        Prestart prestart = new Prestart();
        prestart.setId(prestartId);
        prestartDetailsRepository.deleteAllByPrestart(prestart);

    }


}
