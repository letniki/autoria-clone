package org.example.autoriaclone.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.autoriaclone.dto.ModelDto;
import org.example.autoriaclone.dto.ProducerDto;
import org.example.autoriaclone.dto.UserDto;
import org.example.autoriaclone.dto.consts.CarTypeConst;
import org.example.autoriaclone.dto.consts.RegionConst;
import org.example.autoriaclone.dto.responses.CarResponse;
import org.example.autoriaclone.dto.responses.CarsResponse;
import org.example.autoriaclone.dto.responses.UserResponse;
import org.example.autoriaclone.service.entityServices.CarService;
import org.example.autoriaclone.service.entityServices.ModelService;
import org.example.autoriaclone.service.entityServices.ProducerService;
import org.example.autoriaclone.service.entityServices.UserService;
import org.example.autoriaclone.view.Views;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class BuyerController {
    private final CarService carService;
    private final UserService userService;
    private final ModelService modelService;
    private final ProducerService producerService;
    private final CarTypeConst carTypeConst;
    private final RegionConst regionConst;
    @PostMapping("/register")
    @JsonView({Views.Buyer.class})
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.register(userDto));
    }
    @PostMapping("/views/{id}")
    public String addView(@PathVariable int id){
        carService.addWatchesTotal(id);
        return "Car with id: "+id+" watched";
    }
    @PostMapping("/search/notify-not-found")
    public String notifyNotFound(@RequestParam(required = false) String model, @RequestParam(required = false) String producer){
        return carService.notifyNotFound(model, producer);
    }
    @JsonView(Views.Buyer.class)
    @GetMapping("/search")
    public ResponseEntity<CarsResponse> search(
            @RequestParam(required = false) String producer, @RequestParam(required = false) String model,
            @RequestParam(required = false) String region, @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice, @RequestParam(required = false) String ccy,
            @RequestParam(required = false) String type
    ){
        return ResponseEntity.ok(carService.getCars(producer, model, region, minPrice, maxPrice, ccy, type));
    }
    @GetMapping("/search/producers")
    public ResponseEntity<List<ProducerDto>> getAllProducers(){
        return ResponseEntity.ok(producerService.findAllProducers());
    }
    @GetMapping("/cars/basic/{id}")
    @JsonView({Views.Buyer.class})
    public CarResponse getBasicCarById(@PathVariable int id){
        return carService.findByBasicId(id);
    }
    @GetMapping("/search/models")
    public ResponseEntity<List<ModelDto>> getAllModels(){
        return ResponseEntity.ok(modelService.findAllModels());
    }
    @GetMapping("/search/types")
    public ResponseEntity<CarTypeConst> getAllTypes(){
        return ResponseEntity.ok(carTypeConst);
    }
    @GetMapping("/search/regions")
    public ResponseEntity<RegionConst> getAllRegions(){
        return ResponseEntity.ok(regionConst);
    }
}
