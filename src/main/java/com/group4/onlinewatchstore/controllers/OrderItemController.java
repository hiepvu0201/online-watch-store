package com.group4.onlinewatchstore.controllers;

import com.group4.onlinewatchstore.entities.OrderItem;
import com.group4.onlinewatchstore.exceptions.ResourceNotFoundException;
import com.group4.onlinewatchstore.repositories.OrderItemRepository;
import com.group4.onlinewatchstore.repositories.OrderRepository;
import com.group4.onlinewatchstore.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/orderitem")
public class OrderItemController {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public List<OrderItem> getAllOrderItem(){
        return orderItemRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable(value = "id") Long orderItemId) throws ResourceNotFoundException {

        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(()-> new ResourceNotFoundException("Order Item not found on:" + orderItemId));

        return ResponseEntity.ok().body(orderItem);
    }

    @PostMapping("/add")
    public OrderItem create(@Validated @RequestBody OrderItem orderItem) throws ResourceNotFoundException{
        orderItem.setOrder(orderRepository.findById(orderItem.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderItem.getOrderId())));
        orderItem.setProduct(productRepository.findById(orderItem.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + orderItem.getProductId())));
        return orderItemRepository.save(orderItem);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrderItem> updateName(@PathVariable(value = "id") Long orderItemId,
                                                     @Validated @RequestBody OrderItem orderItemDetails) throws ResourceNotFoundException, Exception{

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Order Item not found on:" + orderItemId));

        orderItem.setOrderId(orderItem.getOrderId());
        orderItem.setOrder(orderRepository.findById(orderItem.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderItem.getOrderId())));
        orderItem.setProductId(orderItem.getProductId());
        orderItem.setProduct(productRepository.findById(orderItem.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + orderItem.getProductId())));
        orderItem.setPrice(orderItem.getPrice());
        orderItem.setQuantity(orderItem.getQuantity());

        final OrderItem updateOrderItem = orderItemRepository.save(orderItem);

        return ResponseEntity.ok(updateOrderItem);
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Boolean> delete(@PathVariable(value = "id") Long orderItemId) throws Exception {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Order Item not found on: " + orderItemId));

        orderItemRepository.delete(orderItem);

        Map<String, Boolean> response = new HashMap<>();

        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
