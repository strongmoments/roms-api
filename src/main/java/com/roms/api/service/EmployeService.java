package com.roms.api.service;


import com.roms.api.model.Employe;
import com.roms.api.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeService {
    @Autowired
    private EmployeRepository employeesRepository;

    public Employe save(Employe employeModel){
        return employeModel = employeesRepository.save(employeModel);

    }

    //@todo find all by organisation id
    public List<Employe> findAll(){
        return employeesRepository.findAll();
    }


}
