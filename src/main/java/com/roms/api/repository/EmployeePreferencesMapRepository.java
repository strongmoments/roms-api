package com.roms.api.repository;

import com.roms.api.model.EmployeePreferencesMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeePreferencesMapRepository extends JpaRepository<EmployeePreferencesMap, String> {

}
