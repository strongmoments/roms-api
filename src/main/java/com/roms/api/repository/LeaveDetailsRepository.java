package com.roms.api.repository;

import com.roms.api.model.LeaveDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveDetailsRepository extends JpaRepository<LeaveDetails,String> {
}
