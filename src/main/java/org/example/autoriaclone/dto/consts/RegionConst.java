package org.example.autoriaclone.dto.consts;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RegionConst {
    private String[] regions = new String[]{"Vinnytska","Volinska","Dnipropetrovska",
            "Donetska","Zhytomyrska","Zakarpatska","Zaporizhska","Ivano-Frankivska",
            "Kyivska","Kyrovohradska","Khersonska","Khmelnytska","Luhanska","Lvivska","Mykolaivska","Odeska","Poltavska",
            "Rivnenska","Sumska","Ternopilska","Kharkivska","Crimea",
            "Cherkaska","Chernivetska","Chernihivska"};
}
