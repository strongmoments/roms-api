package com.roms.api.repository;

import com.roms.api.model.Client;
import com.roms.api.model.EmployeeManagers;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    List<Client> findAllByNameContainingIgnoreCaseAndOrganisation(String searchText, Organisation org);
}
