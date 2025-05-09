package org.example.autoriaclone.mapper;

import org.example.autoriaclone.dto.BasicCarDto;
import org.example.autoriaclone.dto.CarDto;
import org.example.autoriaclone.entity.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {
    public CarDto toDto(Car car){
        return CarDto.builder()
                .id(car.getId())
                .power(car.getPower())
                .model(car.getModel())
                .producer(car.getProducer())
                .year(car.getYear())
                .type(car.getType())
                .details(car.getDetails())
                .runKm(car.getRunKm())
                .engineVolume(car.getEngineVolume())
                .color(car.getColor())
                .region(car.getRegion())
                .place(car.getPlace())
                .transmission(car.getTransmission())
                .gearbox(car.getGearbox())
                .price(car.getPrice())
                .currencyName(car.getCurrencyName())
                .currencyValue(car.getCurrencyValue())
                .checkCount(car.getCheckCount())
                .watchesPerDay(car.getWatchesPerDay())
                .watchesPerWeek(car.getWatchesPerWeek())
                .watchesPerMonth(car.getWatchesPerMonth())
                .active(car.isActive())
                .creationDate(car.getCreationDate())
                .images(car.getImages())
                .build();
    }
    public Car toCar(CarDto carDto){
        return new Car(carDto.getModel(), carDto.getProducer(), carDto.getYear(), carDto.getPower(), carDto.getType(),
                carDto.getDetails(), carDto.getRunKm(), carDto.getEngineVolume(), carDto.getColor(), carDto.getRegion(),
                carDto.getPlace(), carDto.getTransmission(), carDto.getGearbox(), carDto.getPrice(),
                carDto.getCurrencyName(), carDto.getCurrencyValue(), carDto.getCheckCount(), carDto.getWatchesTotal(),
                carDto.getWatchesPerDay(), carDto.getWatchesPerWeek(), carDto.getWatchesPerMonth(), carDto.isActive(),
                carDto.getCreationDate(),carDto.getImages() );
    }
    public BasicCarDto toBasicDto(Car car){
        return BasicCarDto.builder()
                .id(car.getId())
                .model(car.getModel())
                .producer(car.getProducer())
                .year(car.getYear())
                .power(car.getPower())
                .type(car.getType())
                .details(car.getDetails())
                .runKm(car.getRunKm())
                .engineVolume(car.getEngineVolume())
                .color(car.getColor())
                .region(car.getRegion())
                .place(car.getPlace())
                .transmission(car.getTransmission())
                .gearbox(car.getGearbox())
                .price(car.getPrice())
                .currencyName(car.getCurrencyName())
                .currencyValue(car.getCurrencyValue())
                .checkCount(car.getCheckCount())
                .active(car.isActive())
                .creationDate(car.getCreationDate())
                .images(car.getImages())
                .build();
    }
}
