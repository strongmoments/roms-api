package com.roms.api.repository;

import com.roms.api.model.LeaveType;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveTypeRepository extends JpaRepository<LeaveType,String> {

    public List<LeaveType> findAllByOrganisation(Organisation organisation);
}
