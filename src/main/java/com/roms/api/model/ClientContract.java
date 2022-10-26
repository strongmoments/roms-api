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
@JsonIgnoreProperties("hibernatelazyinitializer")
@Entity
@Table(name ="client_contract")
public class ClientContract extends CommonFields implements Serializable {

    @Column(name="name",nullable = false,updatable=false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_idx",referencedColumnName = "id")
    private Client client;

}
