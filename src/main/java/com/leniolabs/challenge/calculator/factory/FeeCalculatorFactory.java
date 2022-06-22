package com.leniolabs.challenge.calculator.factory;

import com.leniolabs.challenge.calculator.FeeCalculatorIF;
import com.leniolabs.challenge.custom.AccountType;
import com.leniolabs.challenge.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
public class FeeCalculatorFactory {

    private final Map<String, FeeCalculatorIF> feeCalculators;

    @Autowired
    public FeeCalculatorFactory(List<FeeCalculatorIF> feeCalculators) {
        // Map all the feeCalculators by the Account Type annotation value
        this.feeCalculators = feeCalculators.stream().collect(Collectors.toMap(
                calculator -> calculator.getClass().getAnnotation(AccountType.class).value(), Function.identity()
        ));
    }

    public Optional<FeeCalculatorIF> getCalculator(Optional<Account> optionalAccount) {
        if(optionalAccount.isPresent()) {
            String type = optionalAccount.get().getAccountType();
            return Optional.ofNullable(feeCalculators.get(type));
        }
        return Optional.empty();
    }

}
