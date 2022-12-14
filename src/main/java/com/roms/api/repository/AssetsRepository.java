package com.roms.api.repository;

import com.roms.api.model.Assets;
import com.roms.api.model.Organisation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetsRepository extends JpaRepository<Assets, String> , JpaSpecificationExecutor<Assets> {

    Page<Assets> findAllByOrganisation(Organisation organisation, PageRequest pageRequest);

    List<Assets> findAllByOrganisationOrderByCreateDateDesc(Organisation organisation);

    //List<Assets> findAllByPredicates
}
