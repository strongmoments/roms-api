package com.roms.api.repository;

import com.roms.api.model.Assets;
import com.roms.api.model.Prestart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestartRepository extends JpaRepository<Prestart, String> , JpaSpecificationExecutor<Prestart> {
}
