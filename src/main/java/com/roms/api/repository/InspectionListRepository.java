package com.roms.api.repository;

import com.roms.api.model.AssetClass;
import com.roms.api.model.AssetType;
import com.roms.api.model.InspectionLists;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InspectionListRepository extends JpaRepository<InspectionLists, String> {

    public List<InspectionLists> findAllByOrganisation(Organisation org);

    public Optional<InspectionLists> findByMakeAndModelAndAssetClassAndAssetType(String make, String model, AssetClass assetClass, AssetType assetType);

}
