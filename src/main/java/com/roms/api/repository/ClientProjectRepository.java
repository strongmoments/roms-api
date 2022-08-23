package com.roms.api.repository;

import com.roms.api.model.ClientProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientProjectRepository extends JpaRepository<ClientProject,String> {
}
