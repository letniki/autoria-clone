package org.example.autoriaclone.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.example.autoriaclone.dto.ModelDto;
import org.example.autoriaclone.dto.ProducerDto;
import org.example.autoriaclone.dto.UserDto;
import org.example.autoriaclone.dto.responses.UserResponse;
import org.example.autoriaclone.service.entityServices.*;
import org.example.autoriaclone.view.Views;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final ModelService modelService;
    private final ProducerService producerService;
    private final ImageService imageService;
    private final CurrencyService currencyService;

    @PostMapping("/managers")
    @RolesAllowed("ADMIN")
    @JsonView({Views.ManagerAdmin.class})
    public UserResponse createManager(@RequestBody UserDto userDto){
        return userService.createManager(userDto);
    }
    @PostMapping("/producer")
    @RolesAllowed("ADMIN")
    public ResponseEntity<ProducerDto> addProducer(@RequestBody ProducerDto producerDto){
        return ResponseEntity.ok(producerService.addProducer(producerDto));
    }
    @PostMapping("/model/{id}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<ModelDto> addModel(@RequestBody ModelDto model, @PathVariable Integer id){
        return ResponseEntity.ok(modelService.addModel(id, model));
    }
    @PostMapping("/currency")
    @RolesAllowed("ADMIN")
    public String uploadCurrencies() throws IOException {
        currencyService.uploadCurrencies();
        return "Currency value Updated...";
    }
    @GetMapping("/managers")
    @RolesAllowed("ADMIN")
    @JsonView({Views.ManagerAdmin.class})
    public ResponseEntity<List<UserDto>> getAllManagers(){
        return ResponseEntity.ok(userService.getAllManagers());
    }
    @PutMapping("/managers/{id}")
    @RolesAllowed("ADMIN")
    @JsonView({Views.ManagerAdmin.class})
    public UserDto setManagerById(@PathVariable int id){
        return userService.setManager(id);
    }
    @PutMapping("/admins/{id}")
    @RolesAllowed("ADMIN")
    @JsonView({Views.ManagerAdmin.class})
    public UserDto setAdminById(@PathVariable int id){
        return userService.setAdmin(id);
    }
    @PutMapping("/sellers/{id}")
    @RolesAllowed("ADMIN")
    @JsonView({Views.ManagerAdmin.class})
    public UserDto setSellerById(@PathVariable int id){
        return userService.setSeller(id);
    }

    @PutMapping("/premium/{id}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<String> setPremiumById(@PathVariable int id){
        return userService.setPremium(id);
    }
    @DeleteMapping("/users/{id}")
    @RolesAllowed("ADMIN")
    public String deleteUserById(@PathVariable int id){
        return userService.deleteUserById(id);
    }
    @DeleteMapping("/images")
    @RolesAllowed("ADMIN")
    public String deleteImageById(@RequestParam Integer carId, @RequestParam Integer imageId){return imageService.deleteImage(imageId, carId);}

}
