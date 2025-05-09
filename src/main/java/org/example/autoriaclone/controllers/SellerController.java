package org.example.autoriaclone.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import io.jsonwebtoken.JwtException;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.autoriaclone.dto.CarDto;
import org.example.autoriaclone.dto.responses.AverageResponse;
import org.example.autoriaclone.dto.responses.CarResponse;
import org.example.autoriaclone.dto.responses.CarsResponse;
import org.example.autoriaclone.service.JwtService;
import org.example.autoriaclone.service.entityServices.CarService;
import org.example.autoriaclone.service.entityServices.UserService;
import org.example.autoriaclone.view.Views;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/cars")
public class SellerController {

    private final UserService userService;
    private final CarService carService;
    private final JwtService jwtService;
    @RolesAllowed({"SELLER","ADMIN","MANAGER"})
    @JsonView(Views.Seller.class)
    @PostMapping
    public ResponseEntity<CarResponse> createCar(@RequestBody @Valid CarDto carDto, @RequestHeader("Authorization") String auth){
        String username= getUsernameFromAuth(auth);
        return ResponseEntity.ok(userService.createCar(carDto, username));
    }
    @JsonView(Views.Seller.class)
    @RolesAllowed({"SELLER","ADMIN","MANAGER"})
    @GetMapping
    public ResponseEntity<CarsResponse> getMyCars(@RequestHeader("Authorization") String auth){
        String username= getUsernameFromAuth(auth);
        return ResponseEntity.ok(userService.getMyCars(username));
    }
    @JsonView(Views.Seller.class)
    @RolesAllowed({"SELLER","ADMIN","MANAGER"})
    @GetMapping("/average-price")
    public ResponseEntity<AverageResponse> findAveragePrice(@RequestHeader("Authorization") String auth,
                                                            @RequestParam String producer, @RequestParam String model,
                                                            @RequestParam(required = false) String ccy,
                                                            @RequestParam(required = false) String region){
        String username= getUsernameFromAuth(auth);
        return ResponseEntity.ok(carService.findAveragePrice(producer, model, ccy, region, username));
    }
    @JsonView(Views.Seller.class)
    @RolesAllowed({"SELLER","ADMIN","MANAGER"})
    @PutMapping("/{id}")
    public ResponseEntity<CarResponse> editMyCar(@PathVariable int id, @RequestBody @Valid CarDto carDto, @RequestHeader("Authorization") String auth){
        String username= getUsernameFromAuth(auth);
        return ResponseEntity.ok(userService.editMyCar(id, carDto, username));
    }
    @JsonView(Views.Seller.class)
    @RolesAllowed({"SELLER","ADMIN","MANAGER"})
    @PutMapping("/img")
    public ResponseEntity<CarResponse> addImage(@RequestParam Integer id, @RequestParam MultipartFile image, @RequestHeader("Authorization") String auth) throws IOException {
        String username= getUsernameFromAuth(auth);
        return ResponseEntity.ok(carService.addImage(id, image, username));
    }
    @RolesAllowed({"SELLER","ADMIN","MANAGER"})
    @DeleteMapping("/{id}")
    public String deleteMyCarById(@PathVariable int id, @RequestHeader("Authorization") String auth){
        String username;
        try{
            username= getUsernameFromAuth(auth);
        }catch (JwtException e){
            return e.getMessage();
        }
        return userService.deleteMyCar(id, username);
    }
    @RolesAllowed({"SELLER","ADMIN","MANAGER"})
    @DeleteMapping("/img")
    public String deleteImage(@RequestParam Integer id, @RequestParam String fileName, @RequestHeader("Authorization") String auth){
        String username= getUsernameFromAuth(auth);
        return carService.deleteImage(id, fileName, username);
    }
    public String getUsernameFromAuth(String auth){
        String token = jwtService.getTokenFromAuth(auth);
        return jwtService.extractUsername(token);
    }
}
