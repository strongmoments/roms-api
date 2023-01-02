package com.roms.api.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name ="assets")
public class Assets  extends CommonFields implements Serializable {

    @Column(name = "asset_no")
    private String assetNo;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_class_idx",referencedColumnName = "id")
    private AssetClass assetClass ;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "year_of_make")
    private String yearOfMake;

    @Column(name = "country_of_origin")
    private String contryOfOrigin;

    @Column(name = "ownership")
    private String ownership;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_type_idx",referencedColumnName = "id")
    private AssetType assetType ;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_category_idx",referencedColumnName = "id")
    private AssetCategory assetCategory ;


    @Column(name = "status")
    private int status;

    @Column(name = "current_rate")
    private String currentRate;

    @Column(name = "current_rate_rider")
    private String currentRateRider;

    @Column(name = "allow_wo")
    private boolean allowWO;

    @Column(name = "retire_asset")
    private boolean retireAsset;

    @Column(name = "retirement_date")
    private Instant retirementDate;

    @Transient
    private String assetImageId;

    @Transient
    private String QrCodeId;

    @OneToOne()
    @JoinColumn(name = "digital_asset_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private DigitalAssets assetImage;

    @OneToOne()
    @JoinColumn(name = "qr_code_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private DigitalAssets QrCode;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_idx",referencedColumnName = "id")
    private Location location ;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inpection_list_idx",referencedColumnName = "id")
    private InspectionLists inspectionLists ;



}
