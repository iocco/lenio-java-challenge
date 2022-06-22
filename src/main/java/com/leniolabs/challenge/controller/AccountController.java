package com.leniolabs.challenge.controller;

import com.leniolabs.challenge.calculator.FeeCalculatorIF;
import com.leniolabs.challenge.calculator.factory.FeeCalculatorFactory;
import com.leniolabs.challenge.model.Account;
import com.leniolabs.challenge.service.AccounServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/lenio-challenge/account/v1")
public class AccountController {

    @Autowired
    private AccounServiceIF accountControllerService;

    @Autowired
    private FeeCalculatorFactory feeCalculatorFactory;

    @PostMapping(value = "/create")
    public ResponseEntity<String> createAccount(@RequestBody Account account) {
        String accountId = accountControllerService.createAccount(account);
        return ResponseEntity.ok(accountId);
    }

    @GetMapping(value = "/calculate-fee/{id}")
    public ResponseEntity<Double> calculateFee(@PathVariable String id) {
        Optional<Account> optionalAccount = accountControllerService.findById(id);
            Optional<FeeCalculatorIF> feeCalculatorIFOptional = feeCalculatorFactory.getCalculator(optionalAccount);
            if(feeCalculatorIFOptional.isPresent()) {
                FeeCalculatorIF feeCalculator = feeCalculatorIFOptional.get();
                return ResponseEntity.ok(feeCalculator.calculateFee());
            }
        // If accounts or the fee for that type does not exist, return not found
        return ResponseEntity.notFound().build();
    }
}
