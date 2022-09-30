package com.roms.api.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties("hibernatelazyinitializer")
@Entity
@Table(name ="digital_assets")
public class DigitalAssets extends CommonFields implements Serializable {

    @Column(name = "url" )
    @Lob
    private String url;

    @Column(name = "file_name" )
    private String fileName;

    @Column(name = "file_type" )
    private String fileType;

    @Column(name = "size" )
    private Long size;

    @Column(name = "reference_id" )
    private String referenceId;

    @Column(name = "reference_type" )
    private String referenceType;


}
