package com.roms.api.repository;

import com.roms.api.model.CommonFields;
import com.roms.api.model.InspectionItems;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InspectionItemRepository extends JpaRepository<InspectionItems, String> {

    public List<InspectionItems> findAllByOrganisation(Organisation org);
}
