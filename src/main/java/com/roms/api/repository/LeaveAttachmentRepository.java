package com.roms.api.repository;

import com.roms.api.model.LeaveAttachments;
import com.roms.api.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveAttachmentRepository extends JpaRepository<LeaveAttachments, String> {

}
