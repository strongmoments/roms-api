package com.roms.api.service;

import com.roms.api.model.Assets;
import com.roms.api.repository.AssetsRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class AssetsService {

    @Autowired
    private AssetsRepository assetsRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;

    public Assets save(Assets model) {
        model.setCreateDate(Instant.now());
        model.setCreateBy(loggedIn.getUser());
        model.setOrganisation(loggedIn.getOrg());
        model = assetsRepository.save(model);
        return  model;
    }

    public Optional<Assets> findById(String assetId){
        return assetsRepository.findById(assetId);
    }

    public Page<Assets> findAll(int page, int size, GenericSpesification spesification){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("createDate").descending());

        return assetsRepository.findAll(spesification,pageble);
    }

    public List<Assets> findAll(){
        return assetsRepository.findAllByOrganisationOrderByCreateDateDesc(loggedIn.getOrg());
    }

}
