package org.example.autoriaclone.service.entityServices;

import lombok.Data;
import org.example.autoriaclone.entity.Car;
import org.example.autoriaclone.entity.Image;
import org.example.autoriaclone.repository.CarRepository;
import org.example.autoriaclone.repository.ImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class ImageService {
    private final CarRepository carRepository;
    private final ImageRepository imageRepository;

    public String deleteImage(Integer imageId, Integer carId){
        try{
            Car car = carRepository.findById(carId).get();
            List<Image> images = car.getImages();
            images.removeIf(image-> image.getId().equals(imageId));
            car.setImages(images);
            carRepository.save(car);
            imageRepository.deleteById(imageId);
            return "Image with id: "+imageId+" was deleted";
        } catch (Exception e){
            return e.getMessage();
        }

    }

}
