package com.ecommerce.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.order.model.Orders;

public interface OrderRepository extends JpaRepository<Orders, Integer> {

}
