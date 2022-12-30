package com.roms.api.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name ="assets_category")
public class AssetCategory extends CommonFields implements Serializable {

    @Column(name = "code" )
    private String code;

    @Column(name = "name" )
    private String name;
}
