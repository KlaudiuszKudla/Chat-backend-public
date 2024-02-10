package com.example.fileservice.repository;

import com.example.fileservice.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {


    Optional<ImageEntity> findByUuid(String uuid);

    @Query(nativeQuery = true, value = "SELECT * FROM image_data WHERE createAt < current_date - interval '2 days' & isUsed=false")
    Optional<List<ImageEntity>> findUnusedImages();
}
