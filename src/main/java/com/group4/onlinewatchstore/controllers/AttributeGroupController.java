package com.group4.onlinewatchstore.controllers;

import com.group4.onlinewatchstore.entities.AttributeGroup;
import com.group4.onlinewatchstore.entities.Brand;
import com.group4.onlinewatchstore.exceptions.ResourceNotFoundException;
import com.group4.onlinewatchstore.repositories.AttributeGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/attributegroup")
public class AttributeGroupController {

    @Autowired
    private AttributeGroupRepository attributeGroupRepository;

    @GetMapping("/")
    public List<AttributeGroup> getAllAttributeGroup(){
        return attributeGroupRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttributeGroup> getAttributeGroupById(@PathVariable(value = "id") Long attributeGroupId) throws ResourceNotFoundException {

        AttributeGroup attributeGroup = attributeGroupRepository.findById(attributeGroupId).orElseThrow(()-> new ResourceNotFoundException("Attribute Group not found on:" + attributeGroupId));

        return ResponseEntity.ok().body(attributeGroup);
    }

    @PostMapping("/add")
    public AttributeGroup create(@Validated @RequestBody AttributeGroup attributeGroup) throws Exception{
        String name = attributeGroup.getName();
        if(name!=null&&!"".equals(name)){
            AttributeGroup tempName = attributeGroupRepository.findByName(name);
            if(tempName!=null){
                throw new Exception("Attribute Group "+name+" is already exist");
            }
        }
        return attributeGroupRepository.save(attributeGroup);
    }

    @PutMapping("/update-name/{id}")
    public ResponseEntity<AttributeGroup> updateName(@PathVariable(value = "id") Long attributeGroupId,
                                            @Validated @RequestBody AttributeGroup attributeGroupDetails) throws ResourceNotFoundException, Exception{

        AttributeGroup attributeGroup = attributeGroupRepository.findById(attributeGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("Attribute Group not found on:" + attributeGroupId));

        AttributeGroup tempName = attributeGroupRepository.findByName(attributeGroupDetails.getName());
        if(tempName!=null){
            throw new Exception("Attribute Group name: "+tempName+" is already exist");
        }

        attributeGroup.setName(attributeGroupDetails.getName());

        final AttributeGroup updateAttributeGroup = attributeGroupRepository.save(attributeGroup);

        return ResponseEntity.ok(updateAttributeGroup);
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Boolean> delete(@PathVariable(value = "id") Long attributeGroupId) throws Exception {
        AttributeGroup attributeGroup = attributeGroupRepository.findById(attributeGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("Attribute Group not found on: " + attributeGroupId));

        attributeGroupRepository.delete(attributeGroup);

        Map<String, Boolean> response = new HashMap<>();

        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
