package com.roms.api.service;

import com.roms.api.repository.InspectionItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InspectionItemsService {

    @Autowired
    protected InspectionItemsRepository inspectionItemsRepository;

}
