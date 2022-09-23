package com.roms.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name ="employee_licences")
public class EmployeeLicence  extends CommonFields implements Serializable {

    @Column(name = "code" )
    private String code;

    @Column(name = "name" )
    private String name;


}
