package org.example.autoriaclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CurrencyDto {
    private Integer id;
    private String ccy;
    private String buy;
    private String sale;
}
