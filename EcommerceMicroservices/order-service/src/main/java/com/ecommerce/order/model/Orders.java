package com.ecommerce.order.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String productName;

    private int quantity;

    private double price;

    private String imageUrl;

    private String username;

    private String orderDate;

    private String status;

    private String paymentMethod;

    private String paymentStatus;

    private String notification;

    public Orders() {
    }

    public Orders(
        int id,
        String productName,
        int quantity,
        double price,
        String imageUrl,
        String username,
        String status
) {

    this.id = id;

    this.productName = productName;

    this.quantity = quantity;

    this.price = price;

    this.imageUrl = imageUrl;

    this.username = username;
    
    this.status = status;
}

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getProductName() {

        return productName;
    }

    public void setProductName(String productName) {

        this.productName = productName;
    }

    public int getQuantity() {

        return quantity;
    }

    public void setQuantity(int quantity) {

        this.quantity = quantity;
    }

    public double getPrice() {

        return price;
    }

    public void setPrice(double price) {

        this.price = price;
    }

    public String getImageUrl() {

        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {

        this.imageUrl = imageUrl;
    }
    public String getUsername() {

    return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }
    
    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    public String getStatus() {

    return status;
    }

    public void setStatus(String status) {

        this.status = status;
    }

    public String getPaymentMethod() {
    return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
    return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getNotification() {
    return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }
}