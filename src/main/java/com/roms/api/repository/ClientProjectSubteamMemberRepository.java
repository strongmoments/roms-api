package com.roms.api.repository;
import com.roms.api.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ClientProjectSubteamMemberRepository extends JpaRepository<ClientProjectSubteamMember,String>{
}
