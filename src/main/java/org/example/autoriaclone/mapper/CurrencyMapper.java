package org.example.autoriaclone.mapper;

import org.example.autoriaclone.dto.CurrencyDto;
import org.example.autoriaclone.entity.Currency;
import org.springframework.stereotype.Component;

@Component
public class CurrencyMapper {
    public CurrencyDto toDto(Currency currency){
        return CurrencyDto.builder()
                .id(currency.getId())
                .ccy(currency.getCcy())
                .buy(currency.getBuy())
                .sale(currency.getSale())
                .build();
    }
    public Currency toEntity(CurrencyDto currencyDto){
        return new Currency(currencyDto.getId(), currencyDto.getCcy(), currencyDto.getBuy(), currencyDto.getSale());
    }
}
