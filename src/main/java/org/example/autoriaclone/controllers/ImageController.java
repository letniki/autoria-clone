package org.example.autoriaclone.controllers;

import lombok.RequiredArgsConstructor;
import org.example.autoriaclone.entity.Image;
import org.example.autoriaclone.service.entityServices.CarService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final CarService carService;
    @GetMapping("get/img/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id){
        try{
            Image image = carService.getImage(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(image.getType()))
                    .body(image.getData());
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }

    }
}
