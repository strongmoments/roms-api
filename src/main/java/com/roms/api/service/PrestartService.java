package com.roms.api.service;

import com.roms.api.model.Assets;
import com.roms.api.model.Prestart;
import com.roms.api.repository.PrestartRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class PrestartService {

    @Autowired
    private PrestartRepository prestartRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;

    public Prestart save(Prestart model){
        model.setCreateBy(loggedIn.getUser());
        model.setCreateDate(Instant.now());
        model.setOrganisation(loggedIn.getOrg());
        return prestartRepository.save(model);
    }

    public Page<Prestart> findAll(int page, int size, GenericSpesification spesification){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("createDate").descending());
        return prestartRepository.findAll(spesification,pageble);
    }

    public List<Prestart> findAll(){
        return prestartRepository.findAll();
    }

}
