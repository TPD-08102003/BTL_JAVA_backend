package org.example.btl_java.repository;

import org.example.btl_java.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
    boolean existsByCategoryName(String categoryName);
}
