package com.group4.onlinewatchstore.controllers;

import com.group4.onlinewatchstore.entities.Order;
import com.group4.onlinewatchstore.exceptions.ResourceNotFoundException;
import com.group4.onlinewatchstore.repositories.OrderRepository;
import com.group4.onlinewatchstore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public List<Order> getAllOrder(){
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable(value = "id") Long orderId) throws ResourceNotFoundException {

        Order order = orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order not found on:" + orderId));

        return ResponseEntity.ok().body(order);
    }

    @PostMapping("/add")
    public Order create(@Validated @RequestBody Order order) throws Exception{
        order.setCreatedBy(userRepository.findById(order.getCreatedById())
                .orElseThrow(() -> new ResourceNotFoundException("Created By not found with id " + order.getCreatedById())));
        order.setModifiedBy(userRepository.findById(order.getModifiedById())
                .orElseThrow(() -> new ResourceNotFoundException("Modified By not found with id " + order.getModifiedById())));
        return orderRepository.save(order);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Order> update(@PathVariable(value = "id") Long orderId,
                                        @Validated @RequestBody Order orderDetails) throws ResourceNotFoundException, Exception{

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found on:" + orderId));

        boolean isdisable = order.isDisabled();
        if(isdisable==true){
            throw new Exception("Order has already been disabled!");
        }

        order.setReceiverName(orderDetails.getReceiverName());
        order.setReceiverPhone(orderDetails.getReceiverPhone());
        order.setReceiverAddress(orderDetails.getReceiverAddress());
        order.setStatus(orderDetails.getStatus());
        order.setNote(orderDetails.getNote());
        order.setCreatedById(orderDetails.getCreatedById());
        order.setCreatedBy(userRepository.findById(order.getCreatedById())
                .orElseThrow(() -> new ResourceNotFoundException("Created By not found with id " + order.getCreatedById())));
        order.setModifiedById(orderDetails.getModifiedById());
        order.setModifiedBy(userRepository.findById(order.getModifiedById())
                .orElseThrow(() -> new ResourceNotFoundException("Modified By not found with id " + order.getModifiedById())));

        final Order updateOrder = orderRepository.save(order);

        return ResponseEntity.ok(updateOrder);
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<Order> disable(@PathVariable(value = "id") Long orderId) throws ResourceNotFoundException, Exception{

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found on:" + orderId));

        boolean isdisable = order.isDisabled();
        if(isdisable==true)
        {
            throw new Exception("Order has already been disabled!");
        }
        order.setDisabled(true);
        final Order updateOrder = orderRepository.save(order);

        return ResponseEntity.ok(updateOrder);
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<Order> enable(@PathVariable(value = "id") Long orderId) throws ResourceNotFoundException, Exception{

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found on:" + orderId));

        boolean isdisable = order.isDisabled();
        if(isdisable==false)
        {
            throw new Exception("Order has not been disabled yet!");
        }
        order.setDisabled(false);
        final Order updateOrder = orderRepository.save(order);

        return ResponseEntity.ok(updateOrder);
    }
}
