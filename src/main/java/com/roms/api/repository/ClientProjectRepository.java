package com.roms.api.repository;

import com.roms.api.model.ClientProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientProjectRepository extends JpaRepository<ClientProject,String> {
}
