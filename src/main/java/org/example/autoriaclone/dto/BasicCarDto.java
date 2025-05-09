package org.example.autoriaclone.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.autoriaclone.entity.Image;
import org.example.autoriaclone.view.Views;

import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class BasicCarDto {
    @JsonView({Views.Buyer.class, Views.Seller.class, Views.ManagerAdmin.class})
    private Integer id;

    @JsonView({Views.Buyer.class, Views.Seller.class, Views.ManagerAdmin.class})
    @NotBlank(message = "model required")
    @Size(min = 1, max = 20, message = "model: min: {min}, max: {max} characters")
    private String model;

    @JsonView({Views.Buyer.class, Views.Seller.class, Views.ManagerAdmin.class})
    @NotBlank(message = "producer required")
    @Size(min = 2, max = 20, message = "producer: min: {min}, max: {max} characters")
    private String producer;

    @JsonView({Views.Buyer.class, Views.Seller.class, Views.ManagerAdmin.class})
    @Min(1900)
    @Max(2023)
    private Integer year;

    @JsonView({Views.Buyer.class, Views.Seller.class, Views.ManagerAdmin.class})
    @Min(value = 50, message = "power: min: {value}")
    private Integer power;

    @JsonView({Views.Buyer.class, Views.Seller.class, Views.ManagerAdmin.class})
    @NotBlank(message = "type required")
    private String type;

    @JsonView({Views.Buyer.class, Views.Seller.class, Views.ManagerAdmin.class})
    private String details;

    @JsonView({Views.Buyer.class, Views.Seller.class, Views.ManagerAdmin.class})
    @NotNull(message = "run km required")
    private Integer runKm;

    @JsonView({Views.Buyer.class, Views.Seller.class, Views.ManagerAdmin.class})
    @DecimalMax("20.0") @DecimalMin("0.0")
    private Double engineVolume;

    @JsonView({Views.Buyer.class, Views.Seller.class, Views.ManagerAdmin.class})
    private String color;

    @JsonView({Views.Buyer.class, Views.Seller.class, Views.ManagerAdmin.class})
    @NotBlank(message = "region required")
    private String region;

    @JsonView({Views.Buyer.class, Views.Seller.class, Views.ManagerAdmin.class})
    @NotBlank(message = "place required")
    private String place;

    @JsonView({Views.Buyer.class, Views.Seller.class, Views.ManagerAdmin.class})
    private String transmission;

    @JsonView({Views.Buyer.class, Views.Seller.class, Views.ManagerAdmin.class})
    private String gearbox;

    @JsonView({Views.Buyer.class, Views.Seller.class, Views.ManagerAdmin.class})
    @NotNull(message = "price is required")
    private Integer price;

    @JsonView({Views.Buyer.class, Views.Seller.class, Views.ManagerAdmin.class})
    @NotBlank(message = "currencyName is required")
    private String currencyName;

    @JsonView({Views.Buyer.class, Views.Seller.class, Views.ManagerAdmin.class})
    private String currencyValue;

    @JsonView({Views.ManagerAdmin.class})
    private Integer checkCount;

    @JsonView({Views.ManagerAdmin.class})
    private boolean active;

    @JsonView({Views.Buyer.class, Views.Seller.class, Views.ManagerAdmin.class})
    private Date creationDate;

    @JsonView({Views.Buyer.class, Views.Seller.class, Views.ManagerAdmin.class})
    private List<Image> images;
}
