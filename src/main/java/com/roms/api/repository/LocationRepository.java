package com.roms.api.repository;

import com.roms.api.model.Location;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location,String> {
    public List<Location> findAllByOrganisation(Organisation org);
}
