package com.ecommerce.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.order.model.Orders;
import com.ecommerce.order.repository.OrderRepository;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/orders")


public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    /* ADD ORDER */

    @PostMapping
    public Orders placeOrder(
            @RequestBody Orders order) {

        return orderRepository.save(order);
    }

    /* GET ALL ORDERS */

    @GetMapping
    public List<Orders> getOrders() {

        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public Orders getOrderById(
            @PathVariable Integer id
    ) {

        return orderRepository
                .findById(id)
                .orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Integer id) {

        orderRepository.deleteById(id);
    }
    @PutMapping("/{id}")
    public Orders updateOrder(
            @PathVariable int id,
            @RequestBody Orders order
    ){
        order.setId(id);
        return orderRepository.save(order);
    }
}