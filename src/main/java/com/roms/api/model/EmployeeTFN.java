package com.roms.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="employee_tfn")
public class EmployeeTFN extends CommonFields implements Serializable {

    @Column(name = "taxpayer_type" )
    private int taxPayerType;
    @Column(name = "tfn_number" )
    private String TFNNumber;
    @Column(name = "have_tfn" )
    private boolean haveTFN;
    @Column(name = "claim_taxfree_threshold" )
    private boolean claimTaxfreeThreshold;
    @Column(name = "have_any_debt" )
    private boolean haveanyDebt;
    @Column(name = "tnc_status" )
    private boolean tncAcceptance;

}
