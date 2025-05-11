package org.example.autoriaclone.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.example.autoriaclone.dto.UserDto;
import org.example.autoriaclone.dto.responses.CarResponse;
import org.example.autoriaclone.dto.responses.UserResponse;
import org.example.autoriaclone.service.entityServices.CarService;
import org.example.autoriaclone.service.entityServices.UserService;
import org.example.autoriaclone.view.Views;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/manage")
public class ManagerController {
    private final UserService userService;
    private final CarService carService;

    @GetMapping("/users")
    @RolesAllowed({"MANAGER","ADMIN"})
    @JsonView({Views.ManagerAdmin.class})
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/cars/{id}")
    @RolesAllowed({"MANAGER","ADMIN"})
    @JsonView({Views.ManagerAdmin.class})
    public CarResponse getCarById(@PathVariable int id){
        return carService.findById(id);
    }
    @PutMapping("/users/ban/{id}")
    @RolesAllowed({"MANAGER", "ADMIN"})
    @JsonView({Views.ManagerAdmin.class})
    public UserResponse banUser(@PathVariable int id){
        return userService.banUser(id);
    }
    @PutMapping("/users/unban/{id}")
    @RolesAllowed({"MANAGER","ADMIN"})
    @JsonView({Views.ManagerAdmin.class})
    public UserResponse unBanUser(@PathVariable int id){return userService.unBanUser(id);}
    @DeleteMapping("/cars/{id}")
    @RolesAllowed({"MANAGER","ADMIN"})
    public String deleteCarById(@PathVariable int id){
        return carService.deleteById(id);
    }
}
