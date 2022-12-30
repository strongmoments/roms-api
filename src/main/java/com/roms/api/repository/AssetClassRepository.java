package com.roms.api.repository;

import com.roms.api.model.AssetClass;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetClassRepository extends JpaRepository<AssetClass, String> {

    List<AssetClass> findAllByOrganisation(Organisation org);
}
