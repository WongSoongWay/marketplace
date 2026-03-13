package com.example.marketplace3.model;

public class CartItemView {

    private Long id;          // CartItem id
    private Product product;  // The product object
    private int quantity;     // CartItem quantity

    public CartItemView(Long id, Product product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    // getters
    public Long getId() { return id; }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }

    // setters (optional)
    public void setQuantity(int quantity) { this.quantity = quantity; }
}