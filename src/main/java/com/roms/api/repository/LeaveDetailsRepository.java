package com.roms.api.repository;

import com.roms.api.model.LeaveDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveDetailsRepository extends JpaRepository<LeaveDetails,String> {
}
