package com.roms.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="employee_type")
public class EmployeType extends CommonFields implements Serializable {
    @Column(name="name",nullable=false)
    private String name;  // type Operator Casual, Operator Permanent, Coperate Casual, Coprate Permanent

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
