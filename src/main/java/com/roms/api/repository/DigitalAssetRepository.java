package com.roms.api.repository;

import com.roms.api.model.DigitalAssets;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DigitalAssetRepository extends JpaRepository<DigitalAssets, String> {

    public List<DigitalAssets> findAllByOrganisation(Organisation organisation);
}
