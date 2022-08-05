package com.roms.api.repository;




import com.roms.api.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    @Query("select  p from Users as p where p.userName=?1")
    public Users findByUsername(String username);
}
