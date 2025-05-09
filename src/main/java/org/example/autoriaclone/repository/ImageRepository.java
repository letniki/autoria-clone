package org.example.autoriaclone.repository;

import org.example.autoriaclone.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    @Transactional
    void deleteByImageName(String imageName);
    @Transactional
    Image findImageById(Integer id);
}
