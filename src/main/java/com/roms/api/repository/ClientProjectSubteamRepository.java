package com.roms.api.repository;


import com.roms.api.model.ClientProjectSubteam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ClientProjectSubteamRepository extends JpaRepository<ClientProjectSubteam, String> {

}
