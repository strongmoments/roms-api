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
@Table(name ="item_category")
public class ItemCategory extends  CommonFields implements Serializable {

    @Column(name = "code" )
    private String code;

    @Column(name = "name" )
    private String name;

}
