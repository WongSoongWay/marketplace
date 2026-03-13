package com.example.marketplace3.model;

public class CartItemView {

    private Long id;          // CartItem id
    private Product product;  // The product object
    private int quantity;     // CartItem quantity
    private boolean visible;

    public CartItemView(Long id, Product product, int quantity, boolean visible) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.visible = visible;
    }

    // getters
    public Long getId() { return id; }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    // setters (optional)
    public void setQuantity(int quantity) { this.quantity = quantity; }
}