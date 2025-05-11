package org.example.autoriaclone.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.autoriaclone.view.Views;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@EnableAutoConfiguration
@ToString
@Table(name = "cars", schema = "public")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private Integer id;
    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private String producer;
    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private String model;
    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private Integer year;
    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private Integer power;
    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private String type;
    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private String details;
    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private Integer runKm;
    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private String region;
    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private String place;
    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private String color;
    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private Double engineVolume;
    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private Integer price;
    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private String transmission;
    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private String gearbox;
    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private String currencyName;
    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private String currencyValue;
    @JsonView({Views.ManagerAdmin.class})
    private Integer checkCount;
    @JsonView({Views.Seller.class, Views.ManagerAdmin.class})
    private Integer watchesPerDay;
    @JsonView({Views.Seller.class, Views.ManagerAdmin.class})
    private Integer watchesPerWeek;
    @JsonView({Views.Seller.class, Views.ManagerAdmin.class})
    private Integer watchesPerMonth;
    @JsonView({Views.Seller.class, Views.ManagerAdmin.class})
    private Integer watchesTotal;
    @JsonView({Views.ManagerAdmin.class})
    private boolean active;
    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private Date creationDate;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "cars_images",
            joinColumns = @JoinColumn(name = "cars_id"),
            inverseJoinColumns = @JoinColumn(name = "images_id")
    )
    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private List<Image> images;

    public Car(String model, String producer, Integer year, Integer power,
               String type, String details, Integer runKm, double engineVolume,
               String color, String region, String place, String transmission,
               String gearbox, Integer price, String currencyName, String currencyValue, Integer checkCount, Integer watchesTotal ,
               Integer watchesPerDay, Integer watchesPerWeek, Integer watchesPerMonth, boolean active, Date creationDate , List<Image> images) {
        this.model = model;
        this.producer = producer;
        this.year = year;
        this.power = power;
        this.type = type;
        this.details = details;
        this.runKm = runKm;
        this.engineVolume = engineVolume;
        this.color = color;
        this.region = region;
        this.place = place;
        this.transmission = transmission;
        this.gearbox = gearbox;
        this.price = price;
        this.currencyName = currencyName;
        this.currencyValue = currencyValue;
        this.checkCount = checkCount;
        this.watchesTotal = watchesTotal;
        this.watchesPerDay = watchesPerDay;
        this.watchesPerWeek = watchesPerWeek;
        this.watchesPerMonth = watchesPerMonth;
        this.active = active;
        this.creationDate = creationDate;
        this.images = images;
    }
    public void update(Car car){

        if(car.getModel()!=null){
            this.model = car.getModel();
        }
        if(car.getProducer()!=null){
            this.producer = car.getProducer();
        }
        if(car.getYear()!=null){
            this.year = car.getYear();
        }
        if(car.getPower()!=null){
            this.power = car.getPower();
        }
        if(car.getType()!=null){
            this.type = car.getType();
        }
        if(car.getDetails()!=null){
            this.details = car.getDetails();
        }
        if(car.getRunKm()!=null){
            this.runKm = car.getRunKm();
        }
        if(car.getEngineVolume()!=null){
            this.engineVolume = car.getEngineVolume();
        }
        if(car.getColor()!=null){
            this.color = car.getColor();
        }
        if(car.getRegion()!=null){
            this.region = car.getRegion();
        }
        if(car.getPlace()!=null){
            this.place = car.getPlace();
        }
        if(car.getTransmission()!=null){
            this.transmission = car.getTransmission();
        }
        if(car.getGearbox()!=null){
            this.gearbox = car.getGearbox();
        }
        if(car.getPrice()!=null){
            this.price = car.getPrice();
        }
        if(car.getCurrencyName()!=null){
            this.currencyName = car.getCurrencyName();
        }
        if(car.getImages()!=null){
            this.images = car.getImages();
        }

    }
    public Integer addCheckCount(){
        this.checkCount++;
        return checkCount;
    }
    public void addWatches(){
        this.watchesTotal++;
        this.watchesPerDay++;
        this.watchesPerWeek++;
        this.watchesPerMonth++;
    }
}
