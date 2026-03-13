package com.example.marketplace3.model;

public class OrderHistoryView {
    private Long orderId;
    private Product product;
    private int quantity;
    private double price;
    private String status;

    public OrderHistoryView(Long orderId, Product product, int quantity, double price, String status) {
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }

    // getters
    public Long getOrderId() { return orderId; }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public String getStatus() { return status; }
}