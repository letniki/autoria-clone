package org.example.autoriaclone.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.autoriaclone.dto.BasicCarDto;
import org.example.autoriaclone.dto.CarDto;
import org.example.autoriaclone.view.Views;

import java.util.List;

@Data
@NoArgsConstructor
public class CarsResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonView({Views.Buyer.class, Views.Seller.class, Views.ManagerAdmin.class})
    private List<BasicCarDto> carsBasic;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonView({Views.Buyer.class, Views.Seller.class, Views.ManagerAdmin.class})
    private List<CarDto> carsPremium;

    @JsonView({Views.Buyer.class, Views.Seller.class, Views.ManagerAdmin.class})
    private Integer amount;

    @JsonView({Views.Buyer.class, Views.Seller.class, Views.ManagerAdmin.class})
    private String error;

    public CarsResponse(List<CarDto> cars) {
        this.carsPremium = cars;
    }

    public CarsResponse setAmount(Integer amount){
        this.amount = amount;
        return this;
    }
    public CarsResponse setError(String error) {
        this.error = error;
        return this;
    }
    public CarsResponse setCarsBasic(List<BasicCarDto> carsBasic){
        this.carsBasic = carsBasic;
        return this;
    }
}
