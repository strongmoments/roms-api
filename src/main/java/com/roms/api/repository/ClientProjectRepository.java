package com.roms.api.repository;

import com.roms.api.model.Client;
import com.roms.api.model.ClientContract;
import com.roms.api.model.ClientProject;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientProjectRepository extends JpaRepository<ClientProject,String> {

    List<ClientProject> findAllByNameContainingIgnoreCaseAndOrganisationAndClientAndContract(String searchText, Organisation organisation, Client client, ClientContract contract);
}
