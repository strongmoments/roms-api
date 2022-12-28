package com.roms.api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="employee_attendance")
public class EmployeeAttendance extends CommonFields {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_idx",referencedColumnName = "id")
    private Employe employe;

    @Column(name="shift")
    private String shift;

    @Column(name="checkin_location")
    private String checkinLocation;

    @Column(name="checkout_location")
    private String checkoutLocation;

    @Column(name="date")
    private Instant date;

    @Column(name="check_in")
    private Instant checkIn;

    @Column(name="check_out")
    private Instant checkOut;

    @Column(name="total_hour")
    private Long totalHour;

    @Column(name="checkin_status")
    private Integer checkInStatus;

    @Column(name="checkin_address")
    private String checkInAddress;

    @Column(name="checkout_address")
    private String checkOutaddress;


    @OneToOne()
    @JoinColumn(name = "subteam_idx",referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private ClientProjectSubteam gang;

    /*@OneToMany(mappedBy="checkin_checkout_history", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<CheckInCheckOutHistory> checkInCheckOutHistory;*/

}
