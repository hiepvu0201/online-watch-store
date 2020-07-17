package com.group4.onlinewatchstore.controllers;

import com.group4.onlinewatchstore.entities.Product;
import com.group4.onlinewatchstore.exceptions.ResourceNotFoundException;
import com.group4.onlinewatchstore.repositories.BrandRepository;
import com.group4.onlinewatchstore.repositories.CategoryRepository;
import com.group4.onlinewatchstore.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    public List<Product> getAllProduct() {

        return productRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id") Long productId) throws ResourceNotFoundException {

        Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product not found on:" + productId));

        return ResponseEntity.ok().body(product);
    }

    @PostMapping("/add")
    public Product create(@Validated @RequestBody Product product) throws Exception{
        String productName = product.getName();

        if(productName!=null&&!"".equals(productName)){
            Product tempProductName = productRepository.findByName(productName);
            if(tempProductName!=null){
                throw new Exception("Product  "+productName+" is already exist");
            }
        }
        product.setBrand(brandRepository.findById(product.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id " + product.getBrandId())));
        product.setCategory(categoryRepository.findById(product.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + product.getCategoryId())));

        return productRepository.save(product);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> update(@PathVariable(value = "id") Long productId,
                                          @Validated @RequestBody Product productDetails) throws ResourceNotFoundException, Exception{

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found on:" + productId));

        boolean isdisable = product.isDisabled();
        if(isdisable==true){
            throw new Exception("Product has already been disabled!");
        }

        product.setSummary(productDetails.getSummary());
        product.setContent(productDetails.getContent());
        product.setUnitPrice(productDetails.getUnitPrice());
        product.setUnitInStock(productDetails.getUnitInStock());
        product.setProductImages(productDetails.getProductImages());
        product.setBrandId(productDetails.getBrandId());
        product.setBrand(brandRepository.findById(product.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id " + product.getBrandId())));
        product.setCategoryId(productDetails.getCategoryId());
        product.setCategory(categoryRepository.findById(product.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + product.getCategoryId())));
        final Product updateProduct = productRepository.save(product);

        return ResponseEntity.ok(updateProduct);
    }

    //Coz we have set product name unique
    @PutMapping("/update-name/{id}")
    public ResponseEntity<Product> updateName(@PathVariable(value = "id") Long productId,
                                              @Validated @RequestBody Product productDetails) throws ResourceNotFoundException, Exception{

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found on:" + productId));

        boolean isdisable = product.isDisabled();
        if(isdisable==true){
            throw new Exception("Product has already been disabled!");
        }

        Product tempProductName = productRepository.findByName(productDetails.getName());
        if(tempProductName!=null){
            throw new Exception("Product  "+tempProductName+" is already exist");
        }

        product.setName(productDetails.getName());
        final Product updateProduct = productRepository.save(product);

        return ResponseEntity.ok(updateProduct);
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<Product> disable(@PathVariable(value = "id") Long productId) throws ResourceNotFoundException, Exception{

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found on:" + productId));

        boolean isdisable = product.isDisabled();
        if(isdisable==true)
        {
            throw new Exception("Product has already been disabled!");
        }
        product.setDisabled(true);
        final Product updateProduct = productRepository.save(product);

        return ResponseEntity.ok(updateProduct);
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<Product> enable(@PathVariable(value = "id") Long productId) throws ResourceNotFoundException, Exception{

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found on:" + productId));

        boolean isdisable = product.isDisabled();
        if(isdisable==false)
        {
            throw new Exception("Product has not been disabled yet!");
        }
        product.setDisabled(false);
        final Product updateProduct = productRepository.save(product);

        return ResponseEntity.ok(updateProduct);
    }
}
