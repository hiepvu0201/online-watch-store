package com.group4.onlinewatchstore.controllers;

import com.group4.onlinewatchstore.entities.Image;
import com.group4.onlinewatchstore.exceptions.ResourceNotFoundException;
import com.group4.onlinewatchstore.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/image")
public class ImageController {

    @Autowired
    private ImageRepository imageRepository;

    @PostMapping("/add")
    public Image uploadImage(@RequestParam("myFile") MultipartFile file) throws IOException {

//        Image img = new Image(file.getOriginalFilename(),file.getSize(),file.getContentType(),file.getBytes());

        Image img = Image.builder()
                .name(file.getOriginalFilename())
                .size(file.getSize())
                .type(file.getContentType())
                .picByte(file.getBytes()).build();

        final Image savedImage = imageRepository.save(img);

        return savedImage;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Image> getImageById(@PathVariable(value = "id") Long ImageId) throws ResourceNotFoundException {

        Image image = imageRepository.findById(ImageId).orElseThrow(()->new ResourceNotFoundException("Image not found on:" + ImageId));

        return ResponseEntity.ok().body(image);
    }

}
