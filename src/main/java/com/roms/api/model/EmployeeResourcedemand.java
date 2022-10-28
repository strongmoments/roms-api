package com.roms.api.model;

import com.roms.api.config.ModelHashMapConverter;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Table(name ="employee_resource_demand")
public class EmployeeResourcedemand extends CommonFields implements Serializable {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hiring_manager_idx",referencedColumnName = "id")
    private Employe hiringManager;

    @Column(name="demand_type")
    private int demandType;

    @Column(name="role_name")
    private String roleName;

    @Column(name="perposedDate")
    private Instant perposedDate;

    @Column(name="description")
    private String description;

    @Column(name="type")
    private int type; // external or internal



    @Column(name="minimum_experience")
    private String minimumExperiecne;

    @Type(type = "jsonb")
    @Column(name = "skils_map", columnDefinition = "json")
    /*@Convert(converter = ModelHashMapConverter.class)*/
    private Map<String, Object> skilsMap;

    @OneToOne()
    @JoinColumn(name = "clientproject_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private ClientProject clientProject;

    @OneToOne()
    @JoinColumn(name = "clientproject_subteam_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private ClientProjectSubteam clientProjectSubteam;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_idx",referencedColumnName = "id")
    private Client client;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_contract_idx",referencedColumnName = "id")
    private ClientContract clientContract;


    private String commitement;




}
