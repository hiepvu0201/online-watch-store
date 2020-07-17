package com.group4.onlinewatchstore.controllers;

import com.group4.onlinewatchstore.entities.Brand;
import com.group4.onlinewatchstore.exceptions.ResourceNotFoundException;
import com.group4.onlinewatchstore.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/brand")
public class BrandController {

    @Autowired
    private BrandRepository brandRepository;

    @GetMapping("/")
    public List<Brand> getAllBrand(){
        return brandRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable(value = "id") Long brandId) throws ResourceNotFoundException {

        Brand brand = brandRepository.findById(brandId).orElseThrow(()-> new ResourceNotFoundException("Brand not found on:" + brandId));

        return ResponseEntity.ok().body(brand);
    }

    @PostMapping("/add")
    public Brand create(@Validated @RequestBody Brand brand) throws Exception{
        String name = brand.getName();
        if(name!=null&&!"".equals(name)){
            Brand tempName = brandRepository.findByName(name);
            if(tempName!=null){
                throw new Exception("Brand "+name+" is already exist");
            }
        }
        return brandRepository.save(brand);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Brand> update(@PathVariable(value = "id") Long brandId,
                                           @Validated @RequestBody Brand brandDetails) throws ResourceNotFoundException, Exception{

        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found on:" + brandId));

        boolean isdisable = brand.isDisabled();
        if(isdisable==true){
            throw new Exception("Brand has already been disabled!");
        }

        brand.setSummary(brandDetails.getSummary());
        brand.setThumbnail(brandDetails.getThumbnail());

        final Brand updateBrand = brandRepository.save(brand);

        return ResponseEntity.ok(updateBrand);
    }

    @PutMapping("/update-name/{id}")
    public ResponseEntity<Brand> updateName(@PathVariable(value = "id") Long brandId,
                                               @Validated @RequestBody Brand brandDetails) throws ResourceNotFoundException, Exception{

        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found on:" + brandId));

        boolean isdisable = brand.isDisabled();
        if(isdisable==true){
            throw new Exception("Brand has already been disabled!");
        }

        Brand tempName = brandRepository.findByName(brandDetails.getName());
        if(tempName!=null){
            throw new Exception("Brand name: "+tempName+" is already exist");
        }

        brand.setName(brandDetails.getName());

        final Brand updateBrand = brandRepository.save(brand);

        return ResponseEntity.ok(updateBrand);
    }

    @PutMapping("/update-slug/{id}")
    public ResponseEntity<Brand> updateSlug(@PathVariable(value = "id") Long brandId,
                                               @Validated @RequestBody Brand brandDetails) throws ResourceNotFoundException, Exception{

        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found on:" + brandId));

        boolean isdisable = brand.isDisabled();
        if(isdisable==true){
            throw new Exception("Brand has already been disabled!");
        }

        Brand tempSlug = brandRepository.findBySlug(brandDetails.getSlug());
        if(tempSlug!=null){
            throw new Exception("Brand name: "+tempSlug+" is already exist");
        }

        brand.setSlug(brandDetails.getSlug());

        final Brand updateBrand = brandRepository.save(brand);

        return ResponseEntity.ok(updateBrand);
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<Brand> disable(@PathVariable(value = "id") Long brandId) throws ResourceNotFoundException, Exception{

        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found on:" + brandId));

        boolean isdisable = brand.isDisabled();
        if(isdisable==true)
        {
            throw new Exception("Brand has already been disabled!");
        }
        brand.setDisabled(true);
        final Brand updateBrand = brandRepository.save(brand);

        return ResponseEntity.ok(updateBrand);
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<Brand> enable(@PathVariable(value = "id") Long brandId) throws ResourceNotFoundException, Exception{

        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found on:" + brandId));

        boolean isdisable = brand.isDisabled();
        if(isdisable==false)
        {
            throw new Exception("Brand has not been disabled yet!");
        }
        brand.setDisabled(false);
        final Brand updateBrand = brandRepository.save(brand);

        return ResponseEntity.ok(updateBrand);
    }

}
