package com.roms.api.service;

import com.roms.api.model.EmployeType;
import com.roms.api.repository.EmployeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeTypeService {
    @Autowired
    private EmployeTypeRepository employeTypeRepository;

    public EmployeType save(EmployeType model){
        return employeTypeRepository.save(model);
    }

}
