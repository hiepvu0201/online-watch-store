package com.group4.onlinewatchstore.controllers;

import com.group4.onlinewatchstore.entities.AttributeGroup;
import com.group4.onlinewatchstore.entities.AttributeValue;
import com.group4.onlinewatchstore.exceptions.ResourceNotFoundException;
import com.group4.onlinewatchstore.repositories.AttributeGroupRepository;
import com.group4.onlinewatchstore.repositories.AttributeValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/attributevalue")
public class AttributeValueController {

    @Autowired
    private AttributeValueRepository attributeValueRepository;

    @Autowired
    private AttributeGroupRepository attributeGroupRepository;

    @GetMapping("/")
    public List<AttributeValue> getAllAttributeValue(){
        return attributeValueRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttributeValue> getAttributeValueById(@PathVariable(value = "id") Long attributeValueId) throws ResourceNotFoundException {

        AttributeValue attributeValue = attributeValueRepository.findById(attributeValueId).orElseThrow(()-> new ResourceNotFoundException("Attribute Value not found on:" + attributeValueId));

        return ResponseEntity.ok().body(attributeValue);
    }

    @PostMapping("/add")
    public AttributeValue create(@Validated @RequestBody AttributeValue attributeValue) throws ResourceNotFoundException{
        attributeValue.setAttributeGroup(attributeGroupRepository.findById(attributeValue.getAttributeGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Attribute Value not found with id " + attributeValue.getAttributeGroupId())));
        return attributeValueRepository.save(attributeValue);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AttributeValue> updateName(@PathVariable(value = "id") Long attributeValueId,
                                                     @Validated @RequestBody AttributeValue attributeValueDetails) throws ResourceNotFoundException, Exception{

        AttributeValue attributeValue = attributeValueRepository.findById(attributeValueId)
                .orElseThrow(() -> new ResourceNotFoundException("Attribute Value not found on:" + attributeValueId));

        attributeValue.setValue(attributeValueDetails.getValue());
        attributeValue.setAttributeGroupId(attributeValueDetails.getAttributeGroupId());
        attributeValue.setAttributeGroup(attributeGroupRepository.findById(attributeValueDetails.getAttributeGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Attribute Group not found with id " + attributeValueDetails.getAttributeGroupId())));

        final AttributeValue updateAttributeValue = attributeValueRepository.save(attributeValue);

        return ResponseEntity.ok(updateAttributeValue);
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Boolean> delete(@PathVariable(value = "id") Long attributeValueId) throws Exception {
        AttributeValue attributeValue = attributeValueRepository.findById(attributeValueId)
                .orElseThrow(() -> new ResourceNotFoundException("Attribute Value not found on: " + attributeValueId));

        attributeValueRepository.delete(attributeValue);

        Map<String, Boolean> response = new HashMap<>();

        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
