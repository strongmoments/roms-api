package com.roms.api.repository;




import com.roms.api.model.Employe;
import com.roms.api.model.Organisation;
import com.roms.api.model.UserRolesMap;
import com.roms.api.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {
    public Optional<Users> findByUserIdEqualsIgnoreCaseAndOrganisation(String userId, Organisation orgId);

    public Optional<Users> findByEmployeIdAndOrganisation(Employe employee, Organisation organisation);

    @Query("SELECT u.userId FROM Users u WHERE u.organisation.id =?1")
    List<String> findAllUserIdByOrganisation(String orgId);

}
