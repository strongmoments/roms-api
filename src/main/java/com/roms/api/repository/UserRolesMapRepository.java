package com.roms.api.repository;

import com.roms.api.model.UserRolesMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRolesMapRepository extends JpaRepository<UserRolesMap,String> {


}
