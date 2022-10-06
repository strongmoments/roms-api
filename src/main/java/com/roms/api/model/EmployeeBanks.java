package com.roms.api.model;

import com.roms.api.requestInput.BankDetailsInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="employee_emergency_contact")
public class EmployeeBanks extends CommonFields implements Serializable {

    @Column(name = "secondary_account" )
    private int secondaryAccount;

    @Column(name = "payslip_by_email" )
    private int payslipByEmail;

    @Column(name = "bank_type" )
    private int bankType;

    @Column(name = "account_holder_name" )
    private String accountHolderName;
    @Column(name = "accountnumber" )
    private String accountNumber;
    @Column(name = "bank_name" )
    private String bankName;
    @Column(name = "bsb_number" )
    private String bsbNumber;

    @Column(name = "fixed_amount" )
    private Integer fixedAmount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_idx",referencedColumnName = "id")
    private Employe employe;

}
