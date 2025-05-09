package org.example.autoriaclone.service.entityServices;

import lombok.Data;
import org.example.autoriaclone.entity.Car;
import org.example.autoriaclone.entity.Image;
import org.example.autoriaclone.repository.CarRepository;
import org.example.autoriaclone.repository.ImageRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Data
@Service
public class ImageService {
    private final CarRepository carRepository;
    private final ImageRepository imageRepository;

    public String deleteImage(Integer imageId, Integer carId){
        Car car = carRepository.findById(carId).get();
        String imageName = imageRepository.findById(imageId).get().getImageName();
        List<Image> images = car.getImages();
        images.removeIf(image-> image.getId().equals(imageId));
        car.setImages(images);
        String path = System.getProperty("user.home") + File.separator + "adImages" + File.separator;
        File f = new File(path+imageName);
        if(!f.delete()){
            return "Failed to delete image";
        }
        carRepository.save(car);
        imageRepository.deleteById(imageId);
        return "Image with id: "+imageId+" was deleted";
    }

}
