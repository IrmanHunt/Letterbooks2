package com.example.Letterbooks.repositories;

import com.example.Letterbooks.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
