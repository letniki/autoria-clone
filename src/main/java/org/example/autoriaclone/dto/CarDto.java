package org.example.autoriaclone.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.autoriaclone.entity.Image;
import org.example.autoriaclone.view.Views;

import java.util.Date;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CarDto {
    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private Integer id;

    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    @NotBlank(message = "model required")
    @Size(min = 1, max = 20, message = "model: min: {min}, max: {max} characters")
    private String model;

    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    @NotBlank(message = "producer required")
    @Size(min = 2, max = 20, message = "producer: min: {min}, max: {max} characters")
    private String producer;

    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    @Min(value = 1900, message = "year: min: {value}")
    @Max(value = 2025, message = "year: max: {value}")
    private Integer year;

    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    @Min(value = 50, message = "power: min: {value}")
    private Integer power;

    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    @NotBlank(message = "type required")
    private String type;

    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private String details;

    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    @NotNull(message = "run km required")
    @Min(value = 0, message = "runKm: min: {value}")
    private Integer runKm;

    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    @DecimalMax(value = "20.0", message = "engineVolume: max: {value}")
    @DecimalMin(value = "0.0", message = "engineVolume: min: {value}")
    private Double engineVolume;

    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private String color;

    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    @NotBlank(message = "region required")
    private String region;

    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    @NotBlank(message = "place required")
    private String place;

    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private String transmission;

    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private String gearbox;

    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    @NotNull(message = "price is required")
    @Min(value = 500, message = "price: min: {value}")
    private Integer price;

    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    @NotBlank(message = "currencyName is required")
    private String currencyName;

    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private String currencyValue;

    @JsonView({Views.ManagerAdmin.class})
    private Integer checkCount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonView({Views.Seller.class, Views.ManagerAdmin.class})
    private Integer watchesTotal;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonView({Views.Seller.class, Views.ManagerAdmin.class})
    private Integer watchesPerDay;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonView({Views.Seller.class, Views.ManagerAdmin.class})
    private Integer watchesPerWeek;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonView({Views.Seller.class, Views.ManagerAdmin.class})
    private Integer watchesPerMonth;

    @JsonView({Views.ManagerAdmin.class})
    private boolean active;

    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private Date creationDate;

    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private List<Image> images;
    public CarDto(){

    }
}
