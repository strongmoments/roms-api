package com.roms.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="prestart_details")
public class PrestartDetails extends CommonFields implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestart_idx",referencedColumnName = "id")
    @JsonBackReference
    private Prestart prestart;

    @OneToOne()
    @JoinColumn(name = "inpection_items_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private InspectionItems item;

    @Column(name="answer")
    private int answer;

    @Column(name="comment")
    private String comment;

    @Transient
    String defectMediaId;

    @OneToOne()
    @JoinColumn(name = "digital_asset_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private DigitalAssets difectMedia;
}
