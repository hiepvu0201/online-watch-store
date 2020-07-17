package com.group4.onlinewatchstore.repositories;

import com.group4.onlinewatchstore.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin(origins = "http://localhost:4200")
public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Category findByName(String name);
    public Category findBySlug(String slug);
}
