package com.roms.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name ="employee_emergency_contact")
public class EmployeeEmergencyContact extends CommonFields implements Serializable {

    @Column(name = "relationship" )
    private String relationship;
    @Column(name = "first_name" )
    public String firstName;
    @Column(name = "last_name" )
    public String lastName;
    @Column(name = "middle_name" )
    private String middleName;
    @Column(name = "salut" )
    private String salut;
    @Column(name = "email" )
    public String email;
    @Column(name = "phone" )
    public String phone;
    @Column(name = "mobile" )
    public String mobile;

    @Column(name = "address")
    private String address;
    @Column(name = "suburb")
    private String  suburb;
    @Column(name = "state")
    private String  state;
    @Column(name = "postcode")
    private String  postcode;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_idx",referencedColumnName = "id")
    private Employe employe;

}
