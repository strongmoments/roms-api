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
@Table(name ="employee_address")
public class EmployeeAddress extends CommonFields implements Serializable {

    @Column(name = "type")
    private int type;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_idx",referencedColumnName = "id")
    private Employe employe;

}
