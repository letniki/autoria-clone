package org.example.autoriaclone.service.entityServices;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.example.autoriaclone.dto.CarDto;
import org.example.autoriaclone.dto.UserDto;
import org.example.autoriaclone.dto.consts.BadWordsConst;
import org.example.autoriaclone.dto.consts.CarTypeConst;
import org.example.autoriaclone.dto.consts.RegionConst;
import org.example.autoriaclone.dto.responses.CarResponse;
import org.example.autoriaclone.dto.responses.CarsResponse;
import org.example.autoriaclone.dto.responses.UserResponse;
import org.example.autoriaclone.entity.Car;
import org.example.autoriaclone.entity.Model;
import org.example.autoriaclone.entity.Producer;
import org.example.autoriaclone.entity.User;
import org.example.autoriaclone.enums.Role;
import org.example.autoriaclone.mapper.CarMapper;
import org.example.autoriaclone.mapper.UserMapper;
import org.example.autoriaclone.repository.*;
import org.example.autoriaclone.service.CustomUserDetailsService;
import org.example.autoriaclone.service.mails.ManagerMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final ProducerRepository producerRepository;
    private final ModelRepository modelRepository;
    private final RegionConst regionConst;
    private final CarTypeConst carTypeConst;
    private final UserMapper userMapper;
    private final CarMapper carMapper;
    private final CurrencyRepository currencyRepository;
    private final CurrencyService currencyService;
    private final BadWordsConst badWordsConst;
    private final ManagerMailSender managerMailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private View error;

    //Buyer:
    public UserResponse register(UserDto userDto){
        User user = userMapper.toEntity(userDto);
        try{
            isUsernameAlreadyExists(user.getUsername());
            isEmailAlreadyUsed(user.getEmail());
            isPhoneNumberAlreadyUsed(user.getPhone());
        }
        catch (IllegalArgumentException e){
            return new UserResponse(null,e.getMessage());
        }

        String encoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(encoded);
        User savedUser = userRepository.save(user.setPremium(false).setEnabled(true).setRole(Role.SELLER.name()));
        return new UserResponse(userMapper.toDto(savedUser),null);
    }

    //Seller:
    public CarResponse createCar( CarDto carDto, String username){
        Car car = carMapper.toCar(carDto);
        User user = userRepository.findByUsername(username);
        CarResponse carResponse = new CarResponse();

        try{
            isValidValues(car);
            carLimit(user);
            currencyService.isValidCurrencyName(car.getCurrencyName());
        }catch (IllegalArgumentException e){
            carResponse.setError(e.getMessage());
            return carResponse;
        }
        if(car.getCheckCount()==null){
            car.setCheckCount(0);
        }
        if(hasBadWords(car.getDetails())) {
            carResponse.setError("bad words used, you have 3 more tries to change it. Tries: " + car.getCheckCount());
            car.setActive(false);
        }else{
            car.setActive(true);
        }
        Car savedCar = addCar(car, user);
        if(user.getPremium()){
            carResponse.setCarPremium(carMapper.toDto(savedCar));
        }else{
            carResponse.setCarBasic(carMapper.toBasicDto(savedCar));
        }
        return carResponse;
    }
    public CarResponse editMyCar(int id, CarDto carDto, String username){
        Car car = carMapper.toCar(carDto);
        User user = userRepository.findByUsername(username);
        List<Car> cars = user.getCars();
        String currency = carDto.getCurrencyName();
        CarResponse carResponse = new CarResponse();
        try{
            isValidValues(car);
            isPersonalCarAndIndex(cars, id);
            currencyService.isValidCurrencyName(currency);
        }catch (IllegalArgumentException e){
            return new CarResponse().setError(e.getMessage());
        }
        Car foundCar = carRepository.findById(id).get();
        if(hasBadWords(car.getDetails())&&foundCar.getCheckCount()<4) {
            foundCar.addCheckCount();
            foundCar.setActive(false);
            carResponse.setError("bad words used, you have 3 more tries to change it. Tries: " + foundCar.getCheckCount());
        }else{foundCar.setActive(true);}
        if(foundCar.getCheckCount()==3){
            carResponse.setError("your car post is not active. Car sent on moderation");
            foundCar.addCheckCount();
            foundCar.setActive(false);
            Car save = carRepository.save(foundCar);
            managerMailSender.sendMail(save);
            return carResponse;
        }
        if(foundCar.getCheckCount()==4){
            carResponse.setError("your car post is not active. Car sent on moderation. Wait for moderator mail answer");
            return carResponse;
        }

        Integer checkCount = foundCar.getCheckCount();
        foundCar.update(car);
        foundCar.setCheckCount(checkCount);

        Car savedCar = carRepository.save(foundCar);
        if(user.getPremium()){
            carResponse.setCarPremium(carMapper.toDto(savedCar));
        }else{
            carResponse.setCarBasic(carMapper.toBasicDto(savedCar));
        }
        return carResponse;
    }
    public Car addCar(Car car, User user){
        car.setCreationDate(new Date(System.currentTimeMillis()));
        car.setWatchesPerDay(0);
        car.setWatchesPerWeek(0);
        car.setWatchesPerMonth(0);
        car.setWatchesTotal(0);
        List<Car> cars = user.getCars();
        car.setCurrencyValue(currencyRepository.findByCcy(car.getCurrencyName()).getSale());
        cars.add(car);
        user.setCars(cars);
        User savedUser = userRepository.save(user);
        List<Car> savedCars = savedUser.getCars();
        return savedCars.get(savedCars.size()-1);
    }
    public String deleteMyCar(int id, String username){
        User user = userRepository.findByUsername(username);
        List<Car> cars = user.getCars();
        try{
            isPersonalCarAndIndex(cars, id);
            Car car = carRepository.findById(id).get();
            cars.remove(car);
            user.setCars(cars);
            userRepository.save(user);
            carRepository.deleteById(id);
            return "Car with id: "+id+" was deleted";
        }catch (IllegalArgumentException e){
            return e.getMessage();
        }
    }
    public CarsResponse getMyCars(String username){
        User user = userRepository.findByUsername(username);
        List<Car> cars = user.getCars();
        if (user.getPremium()){
            return new CarsResponse(cars.stream().map(carMapper::toDto).toList()).setAmount(cars.size());
        }else{
            return new CarsResponse().setCarsBasic(cars.stream().map(carMapper::toBasicDto).toList()).setAmount(cars.size());
        }
    }

    //Manager:
    public List<UserDto> getAllUsers(){
        List<UserDto> allUsers = userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        allUsers.removeIf(userDto -> userDto.getRole().equals("ADMIN"));
        return allUsers;
    }
    public List<UserDto> getAllManagers(){
        return userRepository.findByRole("MANAGER").stream().map(userMapper::toDto).toList();
    }
    public UserResponse banUser(int id){
        try{
            User foundUser = userRepository.findById(id).get();
            foundUser.setEnabled(false);
            User saved = userRepository.save(foundUser);
            return new UserResponse(userMapper.toDto(saved), null);
        } catch (Exception e){
            return new UserResponse(null, e.getMessage());
        }
    }
    public UserResponse unBanUser(int id){
        try{
            User user = userRepository.findById(id).get();
            user.setEnabled(true);
            User saved = userRepository.save(user);
            return new UserResponse(userMapper.toDto(saved),null);
        } catch (Exception e){
            return new UserResponse(null, e.getMessage());
        }
    }

    //Admin:
    public UserResponse createManager(UserDto userDto){
        User user = userMapper.toEntity(userDto);
        try{
            isUsernameAlreadyExists(user.getUsername());
            isEmailAlreadyUsed(user.getEmail());
            isPhoneNumberAlreadyUsed(user.getPhone());

        }catch (IllegalArgumentException e){
            return new UserResponse(null,e.getMessage());
        }
        user.setRole(Role.MANAGER.name());
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saved = userRepository.save(user);
        return new UserResponse(userMapper.toDto(saved), null);
    }
    public UserDto setManager(int id){
        User user = userRepository.findById(id).get();
        user.setRole("MANAGER");
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }
    public UserDto setAdmin(int id){
        User user = userRepository.findById(id).get();
        user.setRole("ADMIN");
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }
    public UserDto setSeller(int id){
        User user = userRepository.findById(id).get();
        user.setRole("SELLER");
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    public String deleteUserById(int id){
        userRepository.deleteById(id);
        return "user with id: "+id+" was deleted";
    }
    public ResponseEntity<String> setPremium(Integer id){
        if (id == null) {
            log.error("id is null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id is null");
        }
        User user;
        try {
            user = userRepository.findById(id).orElse(null);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        if (user == null) {
            log.error("user not found");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
        }
        if (user.getPremium()) {
            log.info("user is already has premium");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("user is already has premium");
        }
        user.setPremium(true);
        userRepository.save(user);
        return ResponseEntity.ok("user with id: "+id+" was set to premium account");
    }


    public void isUsernameAlreadyExists(String username){
        if(userRepository.findByUsername(username) != null){
            throw new IllegalArgumentException("username already exists");
        }
    }
    public void isEmailAlreadyUsed(String email){
        if(userRepository.findByEmail(email) != null){
            throw new IllegalArgumentException("email already in use");
        }
    }
    public void carLimit(User user){
        if(!(user.getPremium() || user.getCars().size()<1)){
            throw new IllegalArgumentException("Not premium account! You can`t upload more than 1 car");
        }
    }
    public void isPersonalCarAndIndex(List<Car> cars, int carId){
        List<Integer> list = cars.stream().map(Car::getId).toList();
        if (!list.contains(carId)){
            throw new IllegalArgumentException("Not legal car id argument");
        }
    }
    public void isPhoneNumberAlreadyUsed(String phone){
        if(userRepository.findByPhone(phone) != null){
            throw new IllegalArgumentException("phone number already used");
        }
    }
    public boolean hasBadWords(String details) {
        String[] badWords = badWordsConst.getBadWords();
        for (String badWord : badWords) {
            if (details.contains(badWord)){
                return true;
            }
        }
        return false;
    }
    public boolean isPremiumAccount(String username){
        return userRepository.findByUsername(username).getPremium();
    }
    public void isValidValues(Car car){
        List<Producer> allProducers = producerRepository.findAll();
        List<String> validProducers = allProducers.stream().map(Producer::getName).toList();
        if (car.getProducer() != null && !validProducers.contains(car.getProducer())) {
            throw new IllegalArgumentException("Not legal producer");
        }
        List<Model> allModels = modelRepository.findAll();
        List<String> validModels = allModels.stream().map(Model::getName).toList();
        if (car.getModel() != null && !validModels.contains(car.getModel())) {
            throw new IllegalArgumentException("Not legal model");
        }
        List<String> allRegions = Arrays.stream(regionConst.getRegions()).toList();
        if (car.getRegion() != null && !allRegions.contains(car.getRegion())) {
            throw new IllegalArgumentException("Not legal region");
        }
        List<String> allTypes = Arrays.stream(carTypeConst.getTypes()).toList();
        if (car.getType() != null && !allTypes.contains(car.getType())) {
            throw new IllegalArgumentException("Not legal type");
        }
    }
    public void isValidValues(String producer, String model, String region, String types){
        List<Producer> allProducers = producerRepository.findAll();
        List<String> validProducers = allProducers.stream().map(Producer::getName).toList();
        if (producer != null && !validProducers.contains(producer)) {
            throw new IllegalArgumentException("Not legal producer");
        }
        List<Model> allModels = modelRepository.findAll();
        List<String> validModels = allModels.stream().map(Model::getName).toList();
        if (model != null && !validModels.contains(model)) {
            throw new IllegalArgumentException("Not legal model");
        }
        List<String> allRegions = Arrays.stream(regionConst.getRegions()).toList();
        if (region != null && !allRegions.contains(region)) {
            throw new IllegalArgumentException("Not legal region");
        }
        List<String> allTypes = Arrays.stream(carTypeConst.getTypes()).toList();
        if (types != null && !allTypes.contains(types)) {
            throw new IllegalArgumentException("Not legal type");
        }
    }
}
