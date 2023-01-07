package com.roms.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="checkin_checkout_history")
public class CheckInCheckOutHistory extends CommonFields{

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_idx",referencedColumnName = "id")
    private Employe employe;

    @Column(name="device_id")
    private String deviceId;

    @Column(name="device_name")
    private String deviceName;

    @Column(name="checkin")
    private Instant checkin;

    @Column(name="checkin_by_system")
    private Instant checkinSystem;

    @Transient
    private String checkinSystemStr;

    @Column(name="total_hour")
    private Long totalHour;

    @Transient
    private String checkinStr;

    @Column(name="checkout")
    private Instant checkout;

    @Column(name="checkout_by_system")
    private Instant checkoutSystem;
    @Transient
    private String checkoutSystemStr;
    @Transient
    private String checkoutStr;

    @Column(name="checkinout_location")
    private String checkOutlocation;

    @Column(name="checkin_location")
    private String checkInlocation;

    @Column(name="checout_address")
    private String checkOutAddress;

    @Column(name="checkin_address")
    private String checkInAddress;

    @Transient
    private String syncId;



}
