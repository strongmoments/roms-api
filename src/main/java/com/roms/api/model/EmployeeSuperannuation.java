package com.roms.api.model;

import com.roms.api.requestInput.CurrentFundInput;
import com.roms.api.requestInput.SelfFundInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="employee_superannuation")
public class EmployeeSuperannuation extends CommonFields implements Serializable {


    @Column(name = "fill_superfund_now" )
    private boolean fillSuperFundNow;

    @Column(name = "paid_asper_mychoice" )
    private boolean paidAsperMychoice;
    @Column(name = "date" )
    private Instant date ;

    @Column(name = "fund_type" )
    private int fundType ;

    @Column(name = "abn" )
    private String abn;
    @Column(name = "fund_name" )
    private String fundName;
    @Column(name = "address" )
    private String address;
    @Column(name = "suburb" )
    private String suburb;
    @Column(name = "state" )
    private String state;
    @Column(name = "post_code" )
    private String postCode;
    @Column(name = "fund_phone" )
    private String fundPhone;
    @Column(name = "usi" )
    private String usi;
    @Column(name = "account_name" )
    private String accountName;
    @Column(name = "member_name" )
    private String membername;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_fund_attachment_idx",referencedColumnName = "id")
    private DigitalAssets currentFundAttachment;

    @Column(name = "esa" )
    private String esa;
    @Column(name = "bsb_code" )
    private String bsbCode;
    @Column(name = "account_number" )
    private String accountNumber;
    @Column(name = "self_tnc_accaptance" )
    private boolean selfTNCAcceptance;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "self_fund_attachment_idx",referencedColumnName = "id")
    private DigitalAssets selfFundattachment;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "signature_idx",referencedColumnName = "id")
    private DigitalAssets signature;

}
