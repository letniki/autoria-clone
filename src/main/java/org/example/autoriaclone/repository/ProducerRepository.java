package org.example.autoriaclone.repository;

import org.example.autoriaclone.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Integer> {
    Producer findProducerByName(String name);
}
