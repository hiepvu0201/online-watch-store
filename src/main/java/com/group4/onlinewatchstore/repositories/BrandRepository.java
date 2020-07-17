package com.group4.onlinewatchstore.repositories;

import com.group4.onlinewatchstore.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin(origins = "http://localhost:4200")
public interface BrandRepository extends JpaRepository<Brand, Long> {
    public Brand findByName(String name);
    public Brand findBySlug(String slug);
}
