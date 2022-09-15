package com.roms.api.repository;

import com.roms.api.model.LeaveExportHistory;
import com.roms.api.model.Organisation;
import com.roms.api.model.ResignationExportHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResignationExportHistoryRepository extends JpaRepository<ResignationExportHistory,String> {

    Page<ResignationExportHistory> findAllByOrganisation(Organisation organisation, PageRequest pageRequest);
}
