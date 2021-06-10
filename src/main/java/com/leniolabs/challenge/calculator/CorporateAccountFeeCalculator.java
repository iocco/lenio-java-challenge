package com.leniolabs.challenge.calculator;

import com.leniolabs.challenge.custom.AccountType;

@AccountType(value = "corporate")
public class CorporateAccountFeeCalculator implements FeeCalculatorIF{

    @Override
    public Double calculateFee() {
        return 0.05;
    }
}
