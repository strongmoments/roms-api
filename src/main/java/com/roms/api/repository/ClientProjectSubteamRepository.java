package com.roms.api.repository;


import com.roms.api.model.ClientProjectSubteam;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClientProjectSubteamRepository extends JpaRepository<ClientProjectSubteam, String> {
     List<ClientProjectSubteam> findAllByOrganisation(Organisation organisation);
     List<ClientProjectSubteam> findAllByTeamNameStartingWithAndOrganisation(String searchKey, Organisation organisation);

}
