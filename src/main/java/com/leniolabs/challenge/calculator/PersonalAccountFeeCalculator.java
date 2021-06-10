package com.leniolabs.challenge.calculator;

import com.leniolabs.challenge.custom.AccountType;

@AccountType(value = "personal")
public class PersonalAccountFeeCalculator implements FeeCalculatorIF{

    @Override
    public Double calculateFee() {
        return 0.01;
    }
}
