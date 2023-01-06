package com.roms.api.repository;

import com.roms.api.model.Prestart;
import com.roms.api.model.PrestartDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestartDetailsRepository extends JpaRepository<PrestartDetails, String> {

    public List<PrestartDetails> findAllByPrestart(Prestart prestart);

    public void deleteAllByPrestart(Prestart prestart);

}
