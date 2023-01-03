package com.roms.api.service;

import com.roms.api.model.ItemCategory;
import com.roms.api.repository.ItemCategoryRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ItemCategoryService {
    @Autowired
    private ItemCategoryRepository itemCategoryRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;


    public ItemCategory save(ItemCategory model){
        model.setCreateBy(loggedIn.getUser());
        model.setOrganisation(loggedIn.getOrg());
        model.setCreateDate(Instant.now());
        return itemCategoryRepository.save(model);
    }

    public List<ItemCategory> findAll(){
        return itemCategoryRepository.findAll();
    }
}
