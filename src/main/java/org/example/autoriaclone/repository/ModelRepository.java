package org.example.autoriaclone.repository;

import org.example.autoriaclone.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {
    Model findByName(String name);
}
