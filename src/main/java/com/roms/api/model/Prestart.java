package com.roms.api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="prestart")
public class Prestart extends CommonFields implements Serializable {

    @Column(name="date")
    private Instant date;

    @Column(name="start_time")
    private Instant startTime;

    @Column(name="end_time")
    private Instant endTime;

    @Column(name="has_defect")
    private boolean hasDefect;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset__idx",referencedColumnName = "id")
    private Assets assets ;

    @Column(name="lat_long")
    private String  locationLatLong;

    @Column(name="location")
    private String  prestartLocation;

    @Transient
    String employeeSelfieImageId;

    @OneToOne()
    @JoinColumn(name = "digital_asset_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private DigitalAssets employeeSelfie;



    @OneToMany(mappedBy="prestart", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<PrestartDetails> prestartDetails;


}
