package org.example.btl_java.repository;

import org.example.btl_java.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Images, Integer> {
   List<Images> findByProductId(Integer productId);
}