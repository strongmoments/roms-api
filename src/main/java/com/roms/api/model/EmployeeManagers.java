package com.roms.api.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name ="employee_managers")
public class EmployeeManagers extends CommonFields implements Serializable {
    @Serial
    private static final long serialVersionUID = -4478409056277770734L;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_idx",referencedColumnName = "id")
    private Employe employe;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "managers_idx",referencedColumnName = "id")
    private Employe managers;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "managers_type_idx",referencedColumnName = "id")
    private EmployeeManagerType employeeManagerType;



    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Employe getManagers() {
        return managers;
    }

    public void setManagers(Employe managers) {
        this.managers = managers;
    }

    public EmployeeManagerType getEmployeeManagerType() {
        return employeeManagerType;
    }

    public void setEmployeeManagerType(EmployeeManagerType employeeManagerType) {
        this.employeeManagerType = employeeManagerType;
    }
}
