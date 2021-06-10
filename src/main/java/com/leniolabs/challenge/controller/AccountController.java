package com.leniolabs.challenge.controller;

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
    public ResponseEntity<Double> calculateFee(@PathVariable String id) throws Exception {
        Optional<Account> account = accountControllerService.findById(id);
        if(!account.isPresent()) {
            throw new Exception("Account not found");
        }
        Double fee = feeCalculatorFactory.getFeeCalculatorImpl(account.get().getAccountType()).calculateFee();
        return ResponseEntity.ok(fee);
    }
}
