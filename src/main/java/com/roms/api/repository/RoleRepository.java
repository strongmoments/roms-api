package com.roms.api.repository;

import com.roms.api.model.Organisation;
import com.roms.api.model.Roles;
import com.roms.api.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Roles, String> {

    @Query("select  p from Roles as p where p.name=?1")
    public Optional<Roles> findByRolename(String roleName);

    public Optional<Roles> findByNameAndOrganisation(String roleName, Organisation org);

    public List<Roles> findAllByOrganisation(Organisation organisation);
}
