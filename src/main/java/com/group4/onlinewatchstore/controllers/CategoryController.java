package com.group4.onlinewatchstore.controllers;

import com.group4.onlinewatchstore.entities.Category;
import com.group4.onlinewatchstore.exceptions.ResourceNotFoundException;
import com.group4.onlinewatchstore.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable(value = "id") Long categoryId) throws ResourceNotFoundException {

        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found on:" + categoryId));

        return ResponseEntity.ok().body(category);
    }

    @PostMapping("/add")
    public Category create(@Validated @RequestBody Category category) throws Exception{
        String name = category.getName();
        if(name!=null&&!"".equals(name)){
            Category tempName = categoryRepository.findByName(name);
            if(tempName!=null){
                throw new Exception("Category "+name+" is already exist");
            }
        }
        return categoryRepository.save(category);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Category> update(@PathVariable(value = "id") Long categoryId,
                                               @Validated @RequestBody Category categoryDetails) throws ResourceNotFoundException, Exception{

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found on:" + categoryId));

        boolean isdisable = category.isDisabled();
        if(isdisable==true){
            throw new Exception("Category has already been disabled!");
        }

        category.setParentId(categoryDetails.getParentId());
        category.setKeywords(categoryDetails.getKeywords());

        final Category updateCategory = categoryRepository.save(category);

        return ResponseEntity.ok(updateCategory);
    }

    @PutMapping("/update-name/{id}")
    public ResponseEntity<Category> updateName(@PathVariable(value = "id") Long categoryId,
                                           @Validated @RequestBody Category categoryDetails) throws ResourceNotFoundException, Exception{

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found on:" + categoryId));

        boolean isdisable = category.isDisabled();
        if(isdisable==true){
            throw new Exception("Category has already been disabled!");
        }

        Category tempName = categoryRepository.findByName(categoryDetails.getName());
        if(tempName!=null){
            throw new Exception("Category name: "+tempName+" is already exist");
        }

        category.setName(categoryDetails.getName());

        final Category updateCategory = categoryRepository.save(category);

        return ResponseEntity.ok(updateCategory);
    }

    @PutMapping("/update-slug/{id}")
    public ResponseEntity<Category> updateSlug(@PathVariable(value = "id") Long categoryId,
                                           @Validated @RequestBody Category categoryDetails) throws ResourceNotFoundException, Exception{

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found on:" + categoryId));

        boolean isdisable = category.isDisabled();
        if(isdisable==true){
            throw new Exception("Category has already been disabled!");
        }

        Category tempSlug = categoryRepository.findBySlug(categoryDetails.getSlug());
        if(tempSlug!=null){
            throw new Exception("Category name: "+tempSlug+" is already exist");
        }

        category.setSlug(categoryDetails.getSlug());

        final Category updateCategory = categoryRepository.save(category);

        return ResponseEntity.ok(updateCategory);
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<Category> disable(@PathVariable(value = "id") Long categoryId) throws ResourceNotFoundException, Exception{

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found on:" + categoryId));

        boolean isdisable = category.isDisabled();
        if(isdisable==true)
        {
            throw new Exception("Category has already been disabled!");
        }
        category.setDisabled(true);
        final Category updateCategory = categoryRepository.save(category);

        return ResponseEntity.ok(updateCategory);
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<Category> enable(@PathVariable(value = "id") Long categoryId) throws ResourceNotFoundException, Exception{

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found on:" + categoryId));

        boolean isdisable = category.isDisabled();
        if(isdisable==false)
        {
            throw new Exception("Category has not been disabled yet!");
        }
        category.setDisabled(false);
        final Category updateCategory = categoryRepository.save(category);

        return ResponseEntity.ok(updateCategory);
    }

}
