package com.roms.api.repository;

import com.roms.api.model.AssetClass;
import com.roms.api.model.AssetType;
import com.roms.api.model.InspectionListMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InspectionListMappingRepository extends JpaRepository<InspectionListMapping, String> {

    List<InspectionListMapping> findAllByInspectionListMakeAndInspectionListModelAndInspectionList_AssetClassAndInspectionList_AssetTypeOrderByInspectionOrderAsc(String make, String model, AssetClass assetclass, AssetType assetType);
}
