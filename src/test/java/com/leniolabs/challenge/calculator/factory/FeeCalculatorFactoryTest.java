package com.leniolabs.challenge.calculator.factory;

import com.leniolabs.challenge.calculator.FeeCalculatorIF;
import com.leniolabs.challenge.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FeeCalculatorFactoryTest {

    @Autowired
    private FeeCalculatorFactory factory;

    @Test
    void contextLoads() {
        assertThat(factory).isNotNull();
    }

    @Test
    public void getCorporateFeeCalculator() {
        Optional<FeeCalculatorIF> optionalCalculator = factory.getCalculator(
                Optional.of(new Account("testId", "name", "lastName", "corporate", 1.0)));

        assertThat(optionalCalculator.isPresent());
        assertThat(optionalCalculator.get().calculateFee()).isEqualTo(0.05);

    }

    @Test
    public void getPersonalFeeCalculator() {
        Optional<FeeCalculatorIF> optionalCalculator = factory.getCalculator(
                Optional.of(new Account("testId", "name", "lastName", "personal", 1.0)));

        assertThat(optionalCalculator.isPresent());
        assertThat(optionalCalculator.get().calculateFee()).isEqualTo(0.01);
    }

    @Test
    public void nonPresentAccount_NotPresentFeeCalculator() {
        Optional<FeeCalculatorIF> optionalCalculator = factory.getCalculator(Optional.empty());
        assertThat(!optionalCalculator.isPresent());
    }

    @Test
    public void nonExistentType_NotPresentFeeCalculator() {
        Optional<FeeCalculatorIF> optionalCalculator = factory.getCalculator(
                Optional.of(new Account("testId", "name", "lastName", "other", 1.0)));

        assertThat(!optionalCalculator.isPresent());
    }

}
