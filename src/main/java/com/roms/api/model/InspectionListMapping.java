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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="inspection_list_mapping")
public class InspectionListMapping extends CommonFields implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inpection_list_idx",referencedColumnName = "id")
    @JsonBackReference
    private InspectionLists inspectionList;

    @OneToOne()
    @JoinColumn(name = "inpection_item_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private InspectionItems inspectionItems;

    @Column(name="inspection_order")
    private int inspectionOrder;

}
