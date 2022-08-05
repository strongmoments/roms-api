package com.roms.api.repository;

import com.roms.api.model.Roles;
import com.roms.api.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Roles, String> {

    @Query("select  p from Roles as p where p.roleName=?1")
    public Optional<Roles> findByRolename(String roleName);
}
