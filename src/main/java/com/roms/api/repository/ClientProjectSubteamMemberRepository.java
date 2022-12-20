package com.roms.api.repository;
import com.roms.api.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ClientProjectSubteamMemberRepository extends JpaRepository<ClientProjectSubteamMember,String>{

    Optional<ClientProjectSubteamMember> findByClientProjectSubteamAndManagerFlagAndOrganisation(ClientProjectSubteam clientProjectSubteam, boolean isManager, Organisation organisation);

    List<ClientProjectSubteamMember> findByOrganisation(Organisation organisation);

    List<ClientProjectSubteamMember> findByEmployeeAndOrganisation(Employe empId, Organisation organisation);



    List<ClientProjectSubteamMember> findByEmployeeAndOrganisationAndManagerFlag(Employe empId, Organisation organisation,boolean flag);
    List<ClientProjectSubteamMember>   findAllByEmployeeEmployeeNoContainsIgnoreCaseAndManagerFlagOrEmployeeFirstNameContainsIgnoreCaseAndManagerFlagAndOrganisation(String employeName,boolean managerFlags, String employeNumber, boolean managerFlag, Organisation organisation);

}
