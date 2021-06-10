package com.leniolabs.challenge.calculator.factory;


import com.leniolabs.challenge.calculator.FeeCalculatorIF;
import com.leniolabs.challenge.custom.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class FeeCalculatorFactory {

    @Autowired
    private Set<FeeCalculatorIF> feeCalculatorsSet;
    
    private static final Map<String, FeeCalculatorIF> feeCalculatorsMap = new HashMap<>();


    @PostConstruct
    public void initFeeCalculatorImpl() {
        feeCalculatorsSet.forEach(feeCalculator -> {
            feeCalculatorsMap.put(feeCalculator.getClass().getAnnotation(AccountType.class).value(), feeCalculator);
        });
    }

    public FeeCalculatorIF getFeeCalculatorImpl(String accountType) {
        FeeCalculatorIF feeCalculator = feeCalculatorsMap.get(accountType);
        if(feeCalculator == null) {
            throw new IllegalArgumentException("Unknown fee calculator impl type: " + accountType);
        }
        return feeCalculator;
    }
}
