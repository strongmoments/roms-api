package com.roms.api.repository;

import com.roms.api.model.CheckInCheckOutHistory;
import com.roms.api.model.Employe;
import com.roms.api.model.Organisation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckInCheckOutHistoryRepository extends JpaRepository<CheckInCheckOutHistory,String> {

    public Page<CheckInCheckOutHistory> findAllByEmployeAndOrganisation(Employe employe, Organisation org, PageRequest pagerequest);

    public Page<CheckInCheckOutHistory> findAllByOrganisation(Organisation org, PageRequest pagerequest);
}
