package com.roms.api.repository;

import com.roms.api.model.UserRolesMap;
import com.roms.api.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRolesMapRepository extends JpaRepository<UserRolesMap,String> {

      @Query("SELECT u FROM UserRolesMap u WHERE u.userId.id =?1")
      List<UserRolesMap> findAllByUserId(String userId);
}
