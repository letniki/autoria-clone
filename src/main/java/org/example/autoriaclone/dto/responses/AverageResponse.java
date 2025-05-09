package org.example.autoriaclone.dto.responses;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.autoriaclone.view.Views;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonView({Views.Buyer.class, Views.Seller.class, Views.ManagerAdmin.class})
public class AverageResponse {
    private String ccy;
    private Integer price;
    private Integer amount;
    private String error;

    public AverageResponse setError(String error) {
        this.error = error;
        return this;
    }
}
