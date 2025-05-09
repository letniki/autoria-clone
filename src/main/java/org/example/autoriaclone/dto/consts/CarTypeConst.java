package org.example.autoriaclone.dto.consts;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CarTypeConst {
    private String[] types = new String[]{"Suv","Hatchback","Sedan",
            "Universal","Coupe","Minivan","Convertible","Crossover","Van","Pickup","Sportcar",
            "Supercar"};
}
