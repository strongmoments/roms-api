package com.roms.api.repository;

import com.roms.api.model.UserPreferencesMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPreferencesMapRepository extends JpaRepository<UserPreferencesMap,String> {
}
