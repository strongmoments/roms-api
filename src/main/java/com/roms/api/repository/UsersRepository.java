package com.roms.api.repository;




import com.roms.api.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

    @Query("select  p from Users as p where p.userName=?1")
    public Optional<Users> findByUsername(String username);
}
