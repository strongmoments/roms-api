package com.roms.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("hibernatelazyinitializer")
@Builder
@Entity
@Table(name ="employee_licences")
public class EmployeeLicence extends CommonFields implements Serializable {

    @Column(name = "issued_in" )
    private String issuedIn;

    @Column(name = "licence_number" )
    private String licenceNumber;
    @Column(name = "expiry_date" )
    private Instant expiryDate;

    @Column(name = "declaration_status" )
    private int declarationStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "licence_front_image_idx",referencedColumnName = "id")
    private DigitalAssets licenceFrontImage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "licence_back_image_idx",referencedColumnName = "id")
    private DigitalAssets licenceBackImage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "signature_idx",referencedColumnName = "id")
    private DigitalAssets signature;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_idx",referencedColumnName = "id")
    private Employe employe;
}
