package com.roms.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="employee_membership")
public class EmployeeMembership extends CommonFields implements Serializable {

    @Column(name = "longservice_scheme" )
    private boolean longServiceLeaveScheme;

    @Column(name = "redundancy_scheme")
    private boolean redundancyScheme;

    @Column(name = "redundancy_scheme_name")
    private String redundancySchemeName;

    @Column(name = "redundancy_scheme_membershipno")
    private String redundancySchemeMemberShipNo;

    @Column(name = "longservice_scheme_name")
    private String longServiceSchemeName;

    @Column(name = "longservice_scheme_membershipno")
    private String longServiceSchemeMemberShipNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_idx",referencedColumnName = "id")
    private Employe employe;


}
