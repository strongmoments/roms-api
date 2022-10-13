package com.roms.api.requestInput;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@Builder
@ToString
public class OnboardingBankingInput implements Serializable {
    private String id;
    private Integer secondaryAccount;
    private Integer payslipByEmail;
    private BankDetailsInput defaultBank;
    private BankDetailsInput secondaryBank;
    private String step;
    private String completionProgress;

}
