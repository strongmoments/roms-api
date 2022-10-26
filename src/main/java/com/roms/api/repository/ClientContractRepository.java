package com.roms.api.repository;

import com.roms.api.model.Client;
import com.roms.api.model.ClientContract;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientContractRepository extends JpaRepository<ClientContract, String> {

    List<ClientContract> findAllByNameContainingIgnoreCaseAndOrganisationAndClient(String searchText, Organisation org, Client client);
}
