package com.roms.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name ="leave_attachments")
public class LeaveAttachments extends CommonFields implements Serializable {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leave_request_idx",referencedColumnName = "id")
    @JsonBackReference
    private LeaveRequest leaveRequestId;

    @OneToOne()
    @JoinColumn(name = "digital_asset_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private DigitalAssets digitalAssets;
}
