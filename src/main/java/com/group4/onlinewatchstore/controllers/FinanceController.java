package com.group4.onlinewatchstore.controllers;

import com.group4.onlinewatchstore.entities.Finance;
import com.group4.onlinewatchstore.entities.Order;
import com.group4.onlinewatchstore.entities.OrderItem;
import com.group4.onlinewatchstore.exceptions.ResourceNotFoundException;
import com.group4.onlinewatchstore.repositories.FinanceRepository;
import com.group4.onlinewatchstore.repositories.OrderRepository;
import com.group4.onlinewatchstore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/finance")
public class FinanceController {

    @Autowired
    private FinanceRepository financeRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public List<Finance> getAllFinance(){
        return financeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Finance> getFinanceById(@PathVariable(value = "id") Long financeId) throws ResourceNotFoundException {

        Finance finance = financeRepository.findById(financeId).orElseThrow(()-> new ResourceNotFoundException("Finance not found on:" + financeId));

        return ResponseEntity.ok().body(finance);
    }

    @PostMapping("/add")
    public Finance create(@Validated @RequestBody Finance finance) throws ResourceNotFoundException{
        finance.setOrder(orderRepository.findById(finance.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + finance.getOrderId())));
        finance.setOrderdBy(userRepository.findById(finance.getOrderById())
                .orElseThrow(() -> new ResourceNotFoundException("Orderd By not found with id " + finance.getOrderById())));
        return financeRepository.save(finance);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Finance> update(@PathVariable(value = "id") Long financeId,
                                                @Validated @RequestBody Finance financeDetails) throws ResourceNotFoundException, Exception{

        Finance finance = financeRepository.findById(financeId)
                .orElseThrow(() -> new ResourceNotFoundException("Finance not found on:" + financeId));

        finance.setAmount(financeDetails.getAmount());
        finance.setOrderId(financeDetails.getOrderId());
        finance.setOrder(orderRepository.findById(financeDetails.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + financeDetails.getOrderId())));
        finance.setOrderById(financeDetails.getOrderById());
        finance.setOrderdBy(userRepository.findById(financeDetails.getOrderById())
                .orElseThrow(() -> new ResourceNotFoundException("Finance not found with id " + financeDetails.getOrderById())));

        final Finance updateFinance = financeRepository.save(finance);

        return ResponseEntity.ok(updateFinance);
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Boolean> delete(@PathVariable(value = "id") Long financeId) throws Exception {
        Finance finance = financeRepository.findById(financeId)
                .orElseThrow(() -> new ResourceNotFoundException("Finance not found on: " + financeId));

        financeRepository.delete(finance);

        Map<String, Boolean> response = new HashMap<>();

        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
