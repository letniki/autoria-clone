package org.example.autoriaclone.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.autoriaclone.dto.BasicCarDto;
import org.example.autoriaclone.dto.CarDto;
import org.example.autoriaclone.view.Views;

@Data
@NoArgsConstructor
public class CarResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private BasicCarDto carBasic;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private CarDto carPremium;

    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private String error;

    public CarResponse(CarDto car){
        this.carPremium = car;
    }
    public CarResponse(BasicCarDto car){
        this.carBasic = car;
    }
    public CarResponse setError(String error) {
        this.error = error;
        return this;
    }

    public CarResponse setCarBasic(BasicCarDto carBasic) {
        this.carBasic = carBasic;
        return this;
    }
}
