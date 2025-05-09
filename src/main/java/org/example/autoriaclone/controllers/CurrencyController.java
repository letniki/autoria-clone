package org.example.autoriaclone.controllers;

import lombok.RequiredArgsConstructor;
import org.example.autoriaclone.dto.responses.CurrencyResponse;
import org.example.autoriaclone.service.entityServices.CurrencyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/currency")
public class CurrencyController {
    private final CurrencyService currencyService;

    @GetMapping
    public List<CurrencyResponse> transferToAllCurrencies(@RequestParam String ccy, @RequestParam String value){
        return currencyService.transferToAllCurrencies(ccy,value);
    }
}
