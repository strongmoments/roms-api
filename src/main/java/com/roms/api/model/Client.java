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
@Builder
@JsonIgnoreProperties("hibernatelazyinitializer")
@Entity
@Table(name ="client")
public class Client extends CommonFields implements Serializable {

    @Column(name="name",nullable = false,updatable=false)
    private String name;

    @Column(name="start_date")
    private Instant startDate;

    @Column(name="end_Date")
    private Instant endDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_idx",referencedColumnName = "id")
    private Location location ;


    public Client(String clientId) {
        super(clientId);
    }
}
