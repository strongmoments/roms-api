package com.roms.api.repository;

import com.roms.api.model.ClientProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientProjectRoleRepository extends JpaRepository<ClientProjectRole,String> {

}
