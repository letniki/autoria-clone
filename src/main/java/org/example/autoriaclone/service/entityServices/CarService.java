package org.example.autoriaclone.service.entityServices;

import lombok.Data;
import org.example.autoriaclone.dto.BasicCarDto;
import org.example.autoriaclone.dto.CarDto;
import org.example.autoriaclone.dto.consts.CarTypeConst;
import org.example.autoriaclone.dto.consts.ImageExtensionsConst;
import org.example.autoriaclone.dto.consts.RegionConst;
import org.example.autoriaclone.dto.responses.AverageResponse;
import org.example.autoriaclone.dto.responses.CarResponse;
import org.example.autoriaclone.dto.responses.CarsResponse;
import org.example.autoriaclone.entity.Car;
import org.example.autoriaclone.entity.Image;
import org.example.autoriaclone.entity.User;
import org.example.autoriaclone.mapper.CarMapper;
import org.example.autoriaclone.repository.*;
import org.example.autoriaclone.service.JwtService;
import org.example.autoriaclone.service.mails.AdminMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Data
@Service
public class CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final UserService userService;
    private final UserRepository userRepository;
    private final CurrencyService currencyService;
    private final AdminMailSender adminMailSender;
    private final ModelRepository modelRepository;
    private final ProducerRepository producerRepository;
    private final ImageExtensionsConst imageExtensionsConst;
    private final ImageRepository imageRepository;
    private final JwtService jwtService;
    private final RegionConst regionConst;
    private final CarTypeConst carTypeConst;
    private final View error;

    public AverageResponse findAveragePrice(String producer, String model, String ccy, String region, String username) {
        if (!userService.isPremiumAccount(username)) {
            return new AverageResponse(null, null, null, "Not premium account");
        }
        try{
            String currency;
            List<Car> cars;
            if (region != null) {
                cars = carRepository.findByProducerAndModelAndActiveAndRegion(producer, model, true, region);
            } else {
                cars = carRepository.findByProducerAndModelAndActive(producer, model, true);
            }
            if (ccy != null) {
                cars.forEach(car -> car.setPrice(
                        currencyService.transferToCcy(ccy, car.getCurrencyName(), car.getPrice())
                ));
                currency = ccy;
            } else {
                cars.forEach(car -> car.setPrice(
                        currencyService.transferToCcy("USD", car.getCurrencyName(), car.getPrice())
                ));
                currency = "USD";
            }
            List<Integer> prices = cars.stream().map(Car::getPrice).toList();
            Integer average = averageCalculator(prices);
            return new AverageResponse( currency, average,prices.size(), null);
        }catch (Exception e){
            return new AverageResponse( null, null, null, e.getMessage());
        }

    }
    public CarResponse findById(int id) {
        try {
            Car car = carRepository.findById(id).get();
            CarDto dto = carMapper.toDto(car);
            return new CarResponse(dto);
        } catch (Exception e) {
            return new CarResponse().setError(e.getMessage());
        }

    }
    public CarResponse findByBasicId(int id) {
        try {
            Car car = carRepository.findById(id).get();
            BasicCarDto dto = carMapper.toBasicDto(car);
            return new CarResponse(dto);
        } catch (Exception e) {
            return new CarResponse().setError(e.getMessage());
        }
    }
    public void addWatchesTotal(int id) {
        Car car = carRepository.findById(id).get();
        car.addWatches();
        carRepository.save(car);
    }


    public String deleteById(int id) {
        Car car = carRepository.findById(id).get();
        User user = userRepository.findByCarsContaining(car);
        List<Car> cars = user.getCars();
        cars.remove(car);
        user.setCars(cars);
        userRepository.save(user);
        carRepository.deleteById(id);
        return "Car with id: " + id + ", was deleted";
    }

    public CarsResponse getCars(String producer, String model, String region, Integer minPrice, Integer maxPrice, String ccy, String type) {
        List<Car> allCars = carRepository.findAllActive();
        try{
            userService.isValidValues(producer, model, region, type);
        }catch (IllegalArgumentException e){
            return new CarsResponse().setError(e.getMessage());
        }
        if (producer != null) {
            allCars.removeIf(car -> !Objects.equals(car.getProducer(), producer));
        }
        if (model != null) {
            allCars.removeIf(car -> !Objects.equals(car.getModel(), model));
        }
        if (region != null) {
            allCars.removeIf(car -> !Objects.equals(car.getRegion(), region));
        }
        if (ccy != null) {
            if (minPrice != null) {
                allCars.removeIf(car -> currencyService.transferToCcy(ccy, car.getCurrencyName(), car.getPrice()) <= minPrice);
            }
            if (maxPrice != null) {
                allCars.removeIf(car -> currencyService.transferToCcy(ccy, car.getCurrencyName(), car.getPrice()) >= maxPrice);
            }
        } else {
            if (minPrice != null) {
                allCars.removeIf(car -> currencyService.transferToCcy("USD", car.getCurrencyName(), car.getPrice()) <= minPrice);
            }
            if (maxPrice != null) {
                allCars.removeIf(car -> currencyService.transferToCcy("USD", car.getCurrencyName(), car.getPrice()) >= maxPrice);
            }
        }
        if (type != null){
            allCars.removeIf(car -> !Objects.equals(car.getType(),type));
        }

        return new CarsResponse(allCars.stream().map(carMapper::toDto).toList()).setAmount(allCars.size());
    }

    public String notifyNotFound(String model, String producer) {
        if (model != null) {
            if (modelRepository.findByName(model) != null) {
                return "Model already exists";
            }
            adminMailSender.sendMail("Model", model);
            return "Admin was notified about missing model: " + model;
        }
        if (producer != null) {
            if (producerRepository.findProducerByName(producer) != null) {
                return "Producer already exists";
            }
                adminMailSender.sendMail("Producer", producer);
                return "Admin was notified about missing producer: " + producer;
        }
        return "No arguments";
    }

    public Image getImage(Integer id) {
        return imageRepository.findImageById(id);
    }


    public CarResponse addImage(int id, MultipartFile file, String username) throws IOException {
        List<Car> cars = userRepository.findByUsername(username).getCars();
        try {
            userService.isPersonalCarAndIndex(cars, id);
        } catch (IllegalArgumentException e) {
            return new CarResponse().setError(e.getMessage());
        }
        String extension = file.getOriginalFilename().split("\\.")[1];
        String[] extensions = imageExtensionsConst.getExtensions();
        List<String> list = Arrays.stream(extensions).toList();
        if (!list.contains(extension)) {
            return new CarResponse()
                    .setError("Not an image file! Supportive image extensions: "
                            + Arrays.toString(extensions)
                            .replace("[", "").replace("]", ""));
        }
        Car car = this.carRepository.findById(id).orElseThrow();
        List<Image> images = car.getImages();
        String title;
        if(images.size()!=0){
            Image lastImage = images.get(images.size() - 1);
            String numberOfPhoto = lastImage.getImageName().split("-")[3].split("\\.")[0];
            title = "car-" + id + "-image-" + (Integer.parseInt(numberOfPhoto)+1) + "." + extension;
        } else{
            title = "car-" + id + "-image-" + 0 + "." + extension;
        }
        String path = System.getProperty("user.home") + File.separator + "adImages" + File.separator;
        Image image = new Image();
        image.setImageName(title);
        image.setType(file.getContentType());
        image.setData(file.getBytes());
        images.add(image);
        file.transferTo(new File(path + title));
        car.setImages(images);
        Car savedCar = carRepository.save(car);
        if(userService.isPremiumAccount(username)){
            return new CarResponse(this.carMapper.toDto(savedCar));
        }else{
            return new CarResponse().setCarBasic(this.carMapper.toBasicDto(savedCar));
        }

    }
    public String deleteImage(Integer id, String filename, String username){
        User user = userRepository.findByUsername(username);
        List<Car> cars = user.getCars();
        try {
            userService.isPersonalCarAndIndex(cars, id);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
        Car car = carRepository.findById(id).get();
        List<Image> images = car.getImages();
        images.removeIf(image -> image.getImageName().equals(filename));
        car.setImages(images);
        carRepository.save(car);
        imageRepository.deleteByImageName(filename);
        return "Image with filename: "+filename+" was deleted";
    }

    public Integer averageCalculator(List<Integer> integers) {
        Integer sum = 0;
        for (Integer integer : integers) {
            sum += integer;
        }
        return sum / integers.size();
    }
}
