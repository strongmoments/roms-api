package com.roms.api.repository;

import com.roms.api.model.EmployeeBanks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeBankRepository  extends JpaRepository<EmployeeBanks,String> {
}
