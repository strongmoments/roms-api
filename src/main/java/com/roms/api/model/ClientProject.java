package com.roms.api.model;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
@Entity
@Table(name="Client_projects")
public class ClientProject extends CommonFields implements Serializable{

    @Serial
    private static final long serialVersionUID = -4962849205291234741L;

    @Column(name="name",nullable = false,updatable=false)
    private String name;

    @Column(name="start_date")
    private Instant startDate;

    @Column(name="end_Date")
    private Instant endDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_idx",referencedColumnName = "id")
    private Client client;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
