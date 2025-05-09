package org.example.autoriaclone.repository;

import org.example.autoriaclone.entity.Car;
import org.example.autoriaclone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByPhone(String phone);
    User findByCarsContaining(Car car);
    List<User> findByRole(String role);

}
